package transaction

import entity.TransactionEntity
import java.time.LocalDateTime


/**
 * An in-memory implementation of the Transaction interface.
 *
 * This class stores TransactionEntity objects in a mutable list and provides simple CRUD operations.
 * Note: Data is not persisted beyond the application runtime.
 *
 * Provided functionalities:
 * - add(transaction): Inserts a new transaction.
 * - getAll(): Returns all stored transactions.
 * - update(transaction): Updates an existing transaction by matching its ID.
 * - delete(id): Removes a transaction with the given ID.
 */
class InMemoryTransaction : Transaction {

    private val transactions = mutableListOf<TransactionEntity>()

    override fun add(transaction: TransactionEntity): Boolean =
        if (validateTransaction(transaction)) transactions.add(transaction) else false


    override fun getAll(): List<TransactionEntity> = transactions


    override fun update(transaction: TransactionEntity): Boolean {
        if (transaction.money.amount < 0) return false

        val index = transactions.indexOfFirst { it.id == transaction.id }
        return if (index != -1) {
            transactions[index] = transaction
            true
        } else {
            false
        }
    }


    override fun delete(id: Int): Boolean = transactions.removeIf { it.id == id }


    private fun validateTransaction(transaction: TransactionEntity): Boolean =
        transactions.none { it.id == transaction.id } &&
                transaction.money.amount >= 0 &&
                transaction.date <= LocalDateTime.now()
}