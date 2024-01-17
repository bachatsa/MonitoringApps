package com.ydzmobile.employee.core.data.database

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ydzmobile.employee.core.data.ResourceState
import com.ydzmobile.employee.core.domain.enum.AttendanceType
import com.ydzmobile.employee.core.domain.model.AttendanceHistoryModel
import com.ydzmobile.employee.core.domain.model.attendance.Attendance
import com.ydzmobile.employee.core.domain.model.auth.User
import com.ydzmobile.employee.core.domain.model.division.Division
import com.ydzmobile.employee.core.domain.model.monitor.TargetModel
import com.ydzmobile.employee.core.domain.model.monitor.TargetModelRequest
import com.ydzmobile.employee.core.extension.toString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset.*
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
    private val db: FirebaseFirestore,
) : DatabaseRepository {
    @SuppressLint("SuspiciousIndentation")
    override fun doAttendance(attendance: Attendance): Flow<ResourceState<Boolean>> {
        attendance.uid = firebaseAuth.uid ?: "-"
        val dbAttendances = db.collection("Attendances")
        return flow {
            emit(value = ResourceState.LOADING())
            dbAttendances.add(attendance).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun createUser(user: User): Flow<ResourceState<Boolean>> {
        val dbUsers: DocumentReference = db.collection("Users").document(user.uid)
        return flow {
            emit(value = ResourceState.LOADING())
            user.idEmployee = Timestamp.now().nanoseconds.toString()
            dbUsers.set(user).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getCurrentUser(): Flow<ResourceState<User>> {
        val dbUsers: DocumentReference = db.collection("Users").document(firebaseAuth.uid ?: "")
        return flow {
            emit(value = ResourceState.LOADING())
            val user = dbUsers.get().await()

            if (user != null) {
                emit(value = ResourceState.SUCCESS(data = user.toObject(User::class.java)!!))
            } else {
                emit(value = ResourceState.ERROR("No such document"))
            }
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun updateCurrentUser(user: User): Flow<ResourceState<Boolean>> {
        val dbUsers: DocumentReference = db.collection("Users").document(user.uid)
        return flow {
            emit(value = ResourceState.LOADING())
            dbUsers.set(user).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it.message.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getDivisions(): Flow<ResourceState<List<Division>>> {
        val dubDivision = db.collection("Divisions")
        return flow {
            emit(value = ResourceState.LOADING())
            val divisions = dubDivision.get().await()

            val result: MutableList<Division> = mutableListOf()

            for (document in divisions) {
                val data = document.data
                val id = document.id
                result.add(Division(id, data["name"] as String, listOf()))
            }
            emit(value = ResourceState.SUCCESS(data = result.toList()))
        }.catch {
            emit(value = ResourceState.ERROR(it?.localizedMessage.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getAllTargets(): Flow<ResourceState<List<TargetModel>>> {
        val dbUsers: DocumentReference = db.collection("Users").document(firebaseAuth.currentUser!!.uid)
        val dbTargets = db.collection("Targets")

        return flow {
            emit(value = ResourceState.LOADING())

            try {
                val user = dbUsers.get().await().toObject(User::class.java)!!
                val targets = dbTargets
                    .whereEqualTo("idEmployee", user.idEmployee)
                    .get().await()

                if (!targets.isEmpty) { // Check if there are any documents
                    val result: MutableList<TargetModel> = mutableListOf()

                    for (document in targets) {
                        val data = document.data
                        val id = document.id
                        result.add(
                            TargetModel(
                                id = id,
                                idEmployee = data["idEmployee"] as String,
                                idDivision = data["idDivision"] as String,
                                dateStart = data["dateStart"] as String,
                                dateFinish = data["dateFinish"] as String,
                                targetBeenDone = (data["targetBeenDone"] as Long).toInt(),
                                totalTarget = (data["totalTarget"] as Long).toInt(),
                                productType = data["productType"] as String,
                                targetType = data["targetType"] as String,
                            )
                        )
                    }

                    Log.d("TARGET", result.toList().toString())
                    emit(value = ResourceState.SUCCESS(data = result.toList()))
                } else {
                    emit(value = ResourceState.ERROR("No documents found"))
                }
            } catch (e: Exception) {

                Log.d("TARGET", "${e.localizedMessage} + ${e.message}")
                emit(value = ResourceState.ERROR(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getMyTargets(targetType: String): Flow<ResourceState<List<TargetModel>>> {
        val dbUsers: DocumentReference = db.collection("Users").document(firebaseAuth.currentUser!!.uid)
        val dbTargets = db.collection("Targets")

        return flow {
            emit(value = ResourceState.LOADING())

            try {
                val user = dbUsers.get().await().toObject(User::class.java)!!
                Log.d("NestedTarget.SubTarget.route", user.idEmployee!! + " " + targetType)
                val targets = if (targetType.isNotEmpty()) {
                    dbTargets
                        .whereEqualTo("idEmployee", user.idEmployee)
                        .whereEqualTo("targetType", targetType)
                        .get().await()
                } else {
                    dbTargets
                        .whereEqualTo("idEmployee", user.idEmployee)
                        .get().await()
                }


                if (!targets.isEmpty) { // Check if there are any documents
                    val result: MutableList<TargetModel> = mutableListOf()

                    for (document in targets) {
                        val data = document.data
                        val id = document.id
                        result.add(
                            TargetModel(
                                id = id,
                                idEmployee = data["idEmployee"] as String,
                                idDivision = data["idDivision"] as String,
                                dateStart = data["dateStart"] as String,
                                dateFinish = data["dateFinish"] as String,
                                targetBeenDone = (data["targetBeenDone"] as Long).toInt(),
                                totalTarget = (data["totalTarget"] as Long).toInt(),
                                productType = data["productType"] as String,
                                targetType = data["targetType"] as String,
                            )
                        )
                    }

                    Log.d("TARGET", result.toList().toString())
                    emit(value = ResourceState.SUCCESS(data = result.toList()))
                } else {
                    emit(value = ResourceState.ERROR("No documents found"))
                }
            } catch (e: Exception) {

                Log.d("TARGET", "${e.localizedMessage} + ${e.message}")
                emit(value = ResourceState.ERROR(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun createTargets(
        division: String,
        target: TargetModelRequest
    ): Flow<ResourceState<Boolean>> {
        val dbTargets = db.collection("Targets")
        return flow {
            emit(value = ResourceState.LOADING())
            dbTargets.add(target).await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it?.localizedMessage.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun updateTarget(
        target: TargetModelRequest,
        idTarget: String,
        uri: Uri
    ): Flow<ResourceState<Boolean>> {
        val dbTargets = db.collection("Targets").document(idTarget)
        val dbProgress = db.collection("Targets").document(idTarget).collection("Progress")

        val storageRef = firebaseStorage.reference
        val fileName = "${System.currentTimeMillis()}_${target.productType}_${idTarget}"

        return flow {
            emit(value = ResourceState.LOADING())
            dbTargets.set(target).await()
            val riversRef = storageRef.child("targetProgress/$fileName")
            riversRef.putFile(uri).await()
            val progress = hashMapOf(
                "targetBeenDone" to target.targetBeenDone,
                "totalTarget" to target.totalTarget,
                "createAt" to FieldValue.serverTimestamp(),
                "picture" to riversRef.path
            )
            dbProgress.add(progress)
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it?.localizedMessage.toString()))
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun deleteTarget(
        target: TargetModelRequest,
        idTarget: String
    ): Flow<ResourceState<Boolean>> {
        val dbTargets = db.collection("Targets").document(idTarget)
        return flow {
            emit(value = ResourceState.LOADING())
            dbTargets.delete().await()
            emit(value = ResourceState.SUCCESS(data = true))
        }.catch {
            emit(value = ResourceState.ERROR(it?.localizedMessage.toString()))
        }
    }

    override fun getHistories(): Flow<ResourceState<List<AttendanceHistoryModel>>> {
        val fourteenDaysAgo = Date.from(Instant.now().minus(14, ChronoUnit.DAYS))
        val currentUser = firebaseAuth.currentUser!!
        val dbAttendances = db.collection("Attendances")
            .whereEqualTo("uid", currentUser.uid)
            .whereGreaterThanOrEqualTo("createAt", fourteenDaysAgo)
        return flow {
            emit(value = ResourceState.LOADING())
            val attendances = dbAttendances.get().await()
            val results: MutableList<AttendanceHistoryModel> = mutableListOf()
            val datePattern = "yyyy-MM-dd HH:mm:ss"

            for (document in attendances) {
                val data = document.data
                val id = document.id
                val attendance = document.toObject(Attendance::class.java)

                val attendanceHistoryModel = AttendanceHistoryModel(
                    id = id,
                    date = attendance.dateTime,
                    dateStart = attendance.createAt.toDate().toString(datePattern),
                    reasonOfPermission = attendance.reasonOfPermission ?: "",
                    symptomsOfIllness = attendance.symptomsOfIllness ?: listOf(),
                    attendanceType = when (attendance.type) {
                        "MASUK" -> AttendanceType.PRESENT
                        "SAKIT" -> AttendanceType.SICK
                        else -> AttendanceType.PERMIT
                    },
                    dateFinish = ""
                )
                val sameElement = results.firstOrNull { it.date == attendanceHistoryModel.date }
                if (sameElement != null) {
                    if (isDateGreaterThan(
                            sameElement.dateStart,
                            attendanceHistoryModel.dateStart,
                            datePattern
                        )
                    ) {
                        sameElement.dateFinish = sameElement.dateStart
                        sameElement.dateStart = attendanceHistoryModel.dateStart
                    } else {
                        sameElement.dateFinish = attendanceHistoryModel.dateStart
                    }
                } else {
                    results.add(attendanceHistoryModel)
                }
            }

            emit(value = ResourceState.SUCCESS(data = results.toList()))
        }.catch {
            emit(value = ResourceState.ERROR(it?.localizedMessage.toString()))
        }
    }

    override fun checkIsAbleToDoAttendance(): Flow<ResourceState<Boolean>> {
        val today = Date()
        val todayString = today.toString("dd-MM-yyyy")
        val currentUser = firebaseAuth.currentUser!!
        val dbAttendances = db.collection("Attendances")
            .whereEqualTo("uid", currentUser.uid)
            .whereEqualTo("dateTime", todayString)
        return flow {
            emit(value = ResourceState.LOADING())
            val attendances = dbAttendances.get().await()
            val results: MutableList<Attendance> = mutableListOf()

            for (document in attendances) {
                results.add(document.toObject(Attendance::class.java))
            }

            Log.d("ATTD", results.toString())
            if ((results.isEmpty() || results.count { element -> element.type == "MASUK" } < 2) && results.count { element -> element.type == "SAKIT" || element.type == "IZIN" } < 1) {
                emit(value = ResourceState.SUCCESS(data = true))
            } else {
                emit(value = ResourceState.ERROR("Mohon maaf anda sudah melakukan Absensi"))
            }
        }.catch {
            emit(value = ResourceState.ERROR(it?.localizedMessage.toString()))
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun isDateGreaterThan(
        dateString1: String,
        dateString2: String,
        format: String
    ): Boolean {
        val sdf = SimpleDateFormat(format)
        val date1 = sdf.parse(dateString1)
        val date2 = sdf.parse(dateString2)

        return date1.after(date2)
    }



    override fun checkIsHasDoAttendance(): Flow<ResourceState<Boolean>> {
        val today = Date()
        val todayString = today.toString("dd-MM-yyyy")
        val currentUser = firebaseAuth.currentUser!!
        val dbAttendances = db.collection("Attendances")
            .whereEqualTo("uid", currentUser.uid)
            .whereEqualTo("dateTime", todayString)
        return flow {
            emit(value = ResourceState.LOADING())
            val attendances = dbAttendances.get().await()
            val results: MutableList<Attendance> = mutableListOf()

            for (document in attendances) {
                results.add(document.toObject(Attendance::class.java))
            }

            Log.d("ATTD", results.toString())
            if (results.count { element -> element.type == "MASUK" } > 0) {
                emit(value = ResourceState.SUCCESS(data = true))
            } else {
                emit(value = ResourceState.ERROR("Mohon maaf anda belum melakukan Absensi"))
            }
        }.catch {
            emit(value = ResourceState.ERROR(it.localizedMessage.toString()))
        }
    }
}