package `in`.sahyadri.samrakshane.sync

/**
 * Integration point for WorkManager.
 *
 * The repository already exposes FIFO pending sync. Add `androidx.work:work-runtime-ktx`
 * and call `repository.syncPending()` from a CoroutineWorker when that dependency is
 * available in the project environment.
 */
class PendingAlertSync
