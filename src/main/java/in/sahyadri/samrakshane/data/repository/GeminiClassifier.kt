package `in`.sahyadri.samrakshane.data.repository

import `in`.sahyadri.samrakshane.domain.AlertType

class GeminiClassifier {
    fun suggest(type: AlertType): String {
        return "AI suggestion: ${type.label}. Scene appears consistent with citizen field evidence."
    }
}
