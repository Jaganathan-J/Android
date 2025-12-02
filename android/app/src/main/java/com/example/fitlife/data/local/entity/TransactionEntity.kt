package com.example.fitlife.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitlife.domain.model.TransactionType

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val typeString: String, // Stored as string from Enum
    val categoryName: String,
    val date: Long,
    val notes: String?
) {
    fun toDomain(): com.example.fitlife.domain.model.Transaction {
        return com.example.fitlife.domain.model.Transaction(
            id = id,
            amount = amount,
            type = TransactionType.valueOf(typeString),
            categoryName = categoryName,
            date = date,
            notes = notes
        )
    }

    companion object {
        fun fromDomain(domain: com.example.fitlife.domain.model.Transaction): TransactionEntity {
            return TransactionEntity(
                id = domain.id,
                amount = domain.amount,
                typeString = domain.type.name,
                categoryName = domain.categoryName,
                date = domain.date,
                notes = domain.notes
            )
        }
    }
}