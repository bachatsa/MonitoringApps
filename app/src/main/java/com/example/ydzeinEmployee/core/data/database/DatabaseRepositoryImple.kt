package com.example.ydzeinEmployee.core.data.database

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject

class DatabaseRepositoryImple @Inject constructor
    (private val firebaseAuth: FirebaseAuth,
     private val firebaseStorage: FirebaseStorage,
     private val db: FirebaseFirestore)
    : DatabaseRepository {




}