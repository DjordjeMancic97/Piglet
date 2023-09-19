package com.djordjemancic.piglet.data.remote

import com.djordjemancic.piglet.data.remote.models.PigletUserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val gson: Gson
) {
    private val userCollection = firestore.collection("users")

    fun signUpUser(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password)

    fun createUserDocument(user: PigletUserModel, authId: String): Task<Void> {
        val mapType = object : TypeToken<Map<String?, Any?>?>() {}.type
        val json = gson.toJson(user)
        val map = gson.fromJson<Map<String, Any>>(json, mapType)
        return userCollection.document(authId).set(map)
    }

    fun signInUser(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)

    fun signOut() = auth.signOut()

    fun getUserDocument(authId: String): Task<DocumentSnapshot> = userCollection.document(authId).get()

    fun getUserAuth(): FirebaseUser? = auth.currentUser
}