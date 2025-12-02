package com.example.fitlife.data.repository

import com.example.fitlife.data.local.TransactionDao
import com.example.fitlife.data.local.entity.TransactionEntity
import com.example.fitlife.domain.model.Transaction
import com.example.fitlife.domain.model.TransactionType
import com.example.fitlife.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        dao.insert(TransactionEntity.fromDomain(transaction))
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.delete(TransactionEntity.fromDomain(transaction))
    }

    override fun getTotalBalance(): Flow<Double> {
        return dao.getAll().map { list ->
            list.sumOf { 
                 if (it.typeString == TransactionType.INCOME.name) it.amount else -it.amount
            }
        }
    }
    
    override fun getSpendingReport(): Flow<Map<Long, Double>> {
        // Simplified: Group expenses by date. Real implementation would aggregate locally.
        return dao.getAll().map { list ->
            list.filter { it.typeString == TransactionType.EXPENSE.name }
                .associate { it.date to it.amount } // Simplification: just raw points
        }
    }
}