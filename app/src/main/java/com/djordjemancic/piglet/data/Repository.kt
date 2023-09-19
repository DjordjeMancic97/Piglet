package com.djordjemancic.piglet.data

import com.djordjemancic.piglet.data.dto.PigletUser
import com.djordjemancic.piglet.data.dto.Transaction
import com.djordjemancic.piglet.data.local.LocalDataSource
import com.djordjemancic.piglet.data.local.entities.PigletUserEntity
import com.djordjemancic.piglet.data.local.entities.TransactionEntity
import com.djordjemancic.piglet.data.local.entities.fromModel
import com.djordjemancic.piglet.data.local.entities.fromTransaction
import com.djordjemancic.piglet.data.local.entities.toUser
import com.djordjemancic.piglet.data.remote.RemoteDataSource
import com.djordjemancic.piglet.data.remote.models.PigletUserModel
import com.djordjemancic.piglet.data.remote.models.fromUser
import com.djordjemancic.piglet.data.remote.models.toEntity
import com.djordjemancic.piglet.other.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class Repository @Inject constructor(
    private val localSource: LocalDataSource,
    private val remoteSource: RemoteDataSource,
) {
    fun signUpUser(user: PigletUser, password: String): Flow<Resource> {
        return flow {
            emit(Resource(status = Resource.Status.LOADING))

            val authResult: AuthResult =
                remoteSource.signUpUser(user.userData.email, password).await()

            if (authResult.user != null) {
                println("Repository signUp user != null")
                val userModel: PigletUserModel = PigletUserModel().fromUser(user, authResult.user!!.uid)
                remoteSource.createUserDocument(userModel, authResult.user!!.uid).await()
                localSource.createOrUpdateUser(userModel.toEntity())
                emit(Resource(status = Resource.Status.SUCCESS))
                return@flow
            }
            println("Repository signUp user == null")
            emit(Resource(
                status = Resource.Status.ERROR,
                errorMessage = "user auth does not exist"
            ))
            return@flow
        }.catch {
            when (it) {
                is FirebaseAuthException -> emit(
                    Resource(
                        status = Resource.Status.ERROR,
                        errorMessage = "${it.message} (${it.errorCode})"
                    )
                )

                is FirebaseFirestoreException -> emit(
                    Resource(
                        status = Resource.Status.ERROR,
                        errorMessage = "${it.message} (${it.code})"
                    )
                )

                else -> emit(
                    Resource(
                        status = Resource.Status.ERROR,
                        errorMessage = "Unknown error occurred - $it"
                    )
                )
            }
            println("Repository signUp catch $it ${user.userData.email}")
        }
    }

    fun signInUser(email: String, password: String): Flow<Resource> {
        return flow {
            emit(Resource(status = Resource.Status.LOADING))
            val authResult: AuthResult = remoteSource.signInUser(email, password).await()

            if (authResult.user == null) {
                println("Repository signIn user == null")
                return@flow
            }

            if (remoteSource.getUserDocument(authResult.user!!.uid).await().exists()) {
                val doc: DocumentSnapshot =
                    remoteSource.getUserDocument(authResult.user!!.uid).await()
                println("Repository signIn data from firestore ${doc.data}")
                val userEntity = PigletUserEntity().fromModel((doc.toObject(PigletUserModel::class.java)!!))
                println("Repository signIn userEntity $userEntity")
                localSource.createOrUpdateUser(userEntity)
                emit(Resource(status = Resource.Status.SUCCESS))
                println("Repository signIn user logged in $userEntity")
                return@flow
            }

            emit(Resource(status = Resource.Status.ERROR, errorMessage = "User not found"))
            println("Repository signIn error")
            return@flow

        }.catch {
            when (it) {
                is FirebaseAuthException -> emit(
                    Resource(
                        status = Resource.Status.ERROR,
                        errorMessage = "${it.message} (${it.errorCode})"
                    )
                )

                is FirebaseFirestoreException -> emit(
                    Resource(
                        status = Resource.Status.ERROR,
                        errorMessage = "${it.message} (${it.code})"
                    )
                )

                else -> emit(
                    Resource(
                        status = Resource.Status.ERROR,
                        errorMessage = "Unknown error occurred - $it"
                    )
                )
            }
            println("Repository signIn exception $it")
        }
    }

    fun signOut() = remoteSource.signOut()

    fun userAlreadyLoggedIn(): Boolean = remoteSource.getUserAuth() != null

    fun getUser(): Flow<Resource> {
        return flow {
            emit(Resource(status = Resource.Status.LOADING))
            val auth: FirebaseUser = remoteSource.getUserAuth()!!
            println("GET USER 1 ${auth.uid}")
            val user: Flow<PigletUserEntity> = localSource.getUser(auth.uid)
            user.collect {
                emit(
                    Resource(
                        status = Resource.Status.SUCCESS,
                        data = it.toUser(),
                    )
                )
            }

        }.catch {
            emit(
                Resource(
                    status = Resource.Status.ERROR,
                    errorMessage = "Error occurred while fetching user data"
                )
            )
            println("GET USER ${it}")
        }
    }

    suspend fun addTransaction(transaction: Transaction) =
        localSource.addOrUpdateTransaction(transaction = TransactionEntity().fromTransaction(transaction, remoteSource.getUserAuth()!!.uid))

}