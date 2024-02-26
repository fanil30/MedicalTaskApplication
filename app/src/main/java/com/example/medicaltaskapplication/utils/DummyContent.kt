package com.example.medicaltaskapplication.utils

import androidx.compose.runtime.SideEffect
import com.example.medicaltaskapplication.data.model.Medication
import com.example.medicaltaskapplication.data.model.Problem

object DummyContent {

    val medicine = listOf(
        Problem(
            "0", "Diabetes", listOf(
                Medication(
                    "Metformin", "500 mg", "500 mg", listOf(

                        "Nausea", "Diarrhea", "Abdominal discomfort"
                    )
                )
            )
        ), Problem(
            "1", "Asthma", listOf(
                Medication(
                    "Albuterol", "2 puffs", "90 mcg", listOf(

                        "Tremor", "Palpitations", "Headache"
                    )
                )
            )
        )
    )

}