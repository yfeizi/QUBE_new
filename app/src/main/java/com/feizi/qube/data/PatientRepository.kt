/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.feizi.qube.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.feizi.qube.data.local.database.Patient
import com.feizi.qube.data.local.database.PatientDao
import javax.inject.Inject

interface PatientRepository {
    val patients: Flow<List<String>>

    suspend fun add(name: String)
}

class DefaultPatientRepository @Inject constructor(
    private val patientDao: PatientDao
) : PatientRepository {

    override val patients: Flow<List<String>> =
        patientDao.getPatients().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        patientDao.insertPatient(Patient(name = name))
    }
}
