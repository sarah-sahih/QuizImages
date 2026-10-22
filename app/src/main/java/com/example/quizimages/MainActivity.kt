package com.example.quizimages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

data class Question(
    val texte: String,
    val image: Int,
    val choix: Array<String>,
    val bonneReponse: Int,
    val description: String
)

class MainActivity : AppCompatActivity() {

    private val questions = arrayOf(
        Question(
            "Combien de côtés possède cette forme ?",
            R.drawable.triangle,
            arrayOf("2 côtés", "3 côtés", "4 côtés"),
            1,
            "Un triangle bleu"
        ),
        Question(
            "Quelle est cette forme ?",
            R.drawable.carre,
            arrayOf("Un carré", "Un cercle", "Un triangle"),
            0,
            "Un carré rouge"
        ),
        Question(
            "Cette forme possède combien de coins ?",
            R.drawable.cercle,
            arrayOf("4 coins", "2 coins", "Aucun coin"),
            2,
            "Un cercle vert"
        ),
        Question(
            "Combien de pointes possède cette étoile ?",
            R.drawable.etoile,
            arrayOf("4 pointes", "5 pointes", "6 pointes"),
            1,
            "Une étoile jaune à cinq pointes"
        ),
        Question(
            "Quelle est cette forme ?",
            R.drawable.rectangle,
            arrayOf("Un cercle", "Un carré", "Un rectangle"),
            2,
            "Un rectangle orange"
        )
    )

    private var indiceQuestion = 0
    private var score = 0
    private var reponseDonnee = false

    private lateinit var txtScore: android.widget.TextView
    private lateinit var txtNumero: android.widget.TextView
    private lateinit var txtQuestion: android.widget.TextView
    private lateinit var imgQuestion: android.widget.ImageView
    private lateinit var txtFeedback: android.widget.TextView
    private lateinit var progressQuiz: android.widget.ProgressBar

    private lateinit var btnChoix1: android.widget.Button
    private lateinit var btnChoix2: android.widget.Button
    private lateinit var btnChoix3: android.widget.Button
    private lateinit var btnSuivant: android.widget.Button
    private lateinit var btnRejouer: android.widget.Button
    private lateinit var txtResultat: android.widget.TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtScore = findViewById(R.id.txt_score)
        txtNumero = findViewById(R.id.txt_numero)
        txtQuestion = findViewById(R.id.txt_question)
        imgQuestion = findViewById(R.id.img_question)
        txtFeedback = findViewById(R.id.txt_feedback)
        progressQuiz = findViewById(R.id.progress_quiz)

        btnChoix1 = findViewById(R.id.btn_choix1)
        btnChoix2 = findViewById(R.id.btn_choix2)
        btnChoix3 = findViewById(R.id.btn_choix3)
        btnSuivant = findViewById(R.id.btn_suivant)
        btnRejouer = findViewById(R.id.btn_rejouer)
        txtResultat = findViewById(R.id.txt_resultat)

        btnChoix1.setOnClickListener { verifierReponse(0) }
        btnChoix2.setOnClickListener { verifierReponse(1) }
        btnChoix3.setOnClickListener { verifierReponse(2) }

        btnSuivant.setOnClickListener {
            indiceQuestion++

            if (indiceQuestion < questions.size) {
                afficherQuestion()
            } else {
                afficherResultat()
            }
        }

        btnRejouer.setOnClickListener {
            indiceQuestion = 0
            score = 0
            progressQuiz.progress = 0
            afficherQuestion()
        }

        afficherQuestion()
    }


    private fun afficherQuestion() {
        val question = questions[indiceQuestion]

        txtQuestion.visibility = android.view.View.VISIBLE
        imgQuestion.visibility = android.view.View.VISIBLE
        txtFeedback.visibility = android.view.View.VISIBLE

        txtNumero.text = "Question ${indiceQuestion + 1} sur ${questions.size}"
        txtScore.text = "Score : $score/${questions.size}"
        txtQuestion.text = question.texte
        imgQuestion.setImageResource(question.image)
        imgQuestion.contentDescription = question.description

        btnChoix1.text = question.choix[0]
        btnChoix2.text = question.choix[1]
        btnChoix3.text = question.choix[2]

        btnChoix1.isEnabled = true
        btnChoix2.isEnabled = true
        btnChoix3.isEnabled = true

        btnChoix1.visibility = android.view.View.VISIBLE
        btnChoix2.visibility = android.view.View.VISIBLE
        btnChoix3.visibility = android.view.View.VISIBLE

        btnSuivant.visibility = android.view.View.GONE
        btnRejouer.visibility = android.view.View.GONE
        txtResultat.visibility = android.view.View.GONE

        txtFeedback.text = ""
        reponseDonnee = false
    }

    private fun verifierReponse(indiceChoix: Int) {
        if (reponseDonnee) {
            return
        }

        reponseDonnee = true

        val question = questions[indiceQuestion]

        if (indiceChoix == question.bonneReponse) {
            score++
            txtFeedback.text = "Bonne réponse !"
        } else {
            txtFeedback.text = "Mauvaise réponse !"
        }

        txtScore.text = "Score : $score/${questions.size}"

        progressQuiz.progress = indiceQuestion + 1

        btnChoix1.isEnabled = false
        btnChoix2.isEnabled = false
        btnChoix3.isEnabled = false

        if (indiceQuestion < questions.size - 1) {
            btnSuivant.text = "Question suivante"
        } else {
            btnSuivant.text = "Voir le résultat"
        }

        btnSuivant.visibility = android.view.View.VISIBLE
    }

    private fun afficherResultat() {
        txtNumero.text = "Quiz terminé !"

        txtQuestion.visibility = android.view.View.GONE
        imgQuestion.visibility = android.view.View.GONE
        txtFeedback.visibility = android.view.View.GONE

        btnChoix1.visibility = android.view.View.GONE
        btnChoix2.visibility = android.view.View.GONE
        btnChoix3.visibility = android.view.View.GONE
        btnSuivant.visibility = android.view.View.GONE

        txtResultat.text = "Votre score final : $score/${questions.size}"
        txtResultat.visibility = android.view.View.VISIBLE

        btnRejouer.visibility = android.view.View.VISIBLE
    }

}
