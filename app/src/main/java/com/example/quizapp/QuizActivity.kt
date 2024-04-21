package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp.databinding.ActivityLoginBinding
import com.example.quizapp.databinding.ActivityQuizBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class QuizActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityQuizBinding
    private lateinit var list : ArrayList<QuestionModel>
    private var count:Int = 0
    private var score= 0
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(this.layoutInflater)

        setContentView(binding.root)

        list=ArrayList<QuestionModel>()
        Firebase.firestore.collection("quiz")
            .get().addOnSuccessListener{
                doct->
                list.clear()

                for(i in doct.documents){

                    var questionModel = i.toObject(QuestionModel::class.java)
                    list.add(questionModel!!)
                }
                this.binding.question.setText(list.get(0).question)
                this.binding.option1.setText(list.get(0).option1)
                this.binding.option2.setText(list.get(0).option2)
                this.binding.option3.setText(list.get(0).option3)
                this.binding.option4.setText(list.get(0).option4)
            }
      /*  list.add(QuestionModel("Who is the PM of India?","Narendra Modi","Rahul Gandhi","Yogi Aditya", "Arvind Kejriwal","Narendra Modi"))
        list.add(QuestionModel("Who is the CM of U.P.?","Narendra Modi","Rahul Gandhi","Yogi Aditya", "Arvind Kejriwal","Yogi Aditya"))
        list.add(QuestionModel("Who is the CM of Delhi?","Narendra Modi","Rahul Gandhi","Yogi Aditya", "Arvind Kejriwal","Arvind Kejriwal"))
        list.add(QuestionModel("Who is the leader of opposition Comgress?","Narendra Modi","Rahul Gandhi","Yogi Aditya", "Arvind Kejriwal","Rahul Gandhi"))
        list.add(QuestionModel("Who is the previous PM of India?","Narendra Modi","Rahul Gandhi","Yogi Aditya", "Arvind Kejriwal","Narendra Modi"))
*/
        binding.option1.setOnClickListener{
            nextData(binding.option1.text.toString())
        }
        binding.option2.setOnClickListener{
            nextData(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener{
            nextData(binding.option3.text.toString())
        }
        binding.option4.setOnClickListener{
            nextData(binding.option4.text.toString())
        }

    }

    private fun nextData(i: String) {
        if(count<list.size) {
            if (list.get(count).ans.equals(i)) {
                score++
            }
        }
        count++
        if(count>=list.size)
        {
            val intent= Intent(this, ScoreActivity::class.java)
            intent.putExtra("SCORE",score)
            startActivity(intent)
            //Toast.makeText(this@QuizActivity, score.toString(), Toast.LENGTH_LONG).show()

            finish()
        }

        else
        {
        this.binding.question.setText(list.get(count).question)
        this.binding.option1.setText(list.get(count).option1)
        this.binding.option2.setText(list.get(count).option2)
        this.binding.option3.setText(list.get(count).option3)
        this.binding.option4.setText(list.get(count).option4)
        }
    }


}
