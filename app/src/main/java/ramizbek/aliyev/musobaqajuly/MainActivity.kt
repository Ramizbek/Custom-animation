package ramizbek.aliyev.musobaqajuly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

object My {
    var link: String = ""
}

class MainActivity : AppCompatActivity() {
    private var handlerAnimation = Handler()
    private lateinit var reference: DatabaseReference

    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("anim")

        firebaseDatabase.getReference("anim").child("anim")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val link = snapshot.value.toString()
                    println(link)
                    if (link == "start") {
                        startPulse()
                        button.setText(R.string.start)
                    } else if (link == "stop") {
                        stopPulse()
                        button.setText(R.string.stop)
                    }
                    My.link = link


                }


                override fun onCancelled(error: DatabaseError) {
                }
            })

        button.setOnClickListener {
            println("My link ${My.link}")
            if (My.link == "stop") {
                startPulse()
                reference.child("anim").setValue("start")
                My.link = "start"
                button.setText(R.string.stop)
            } else if (My.link == "start") {
                stopPulse()
                reference.child("anim").setValue("stop")
                My.link = "stop"
                button.setText(R.string.start)

            }
        }


    }

    private fun startPulse() {
        runnable.run()
    }

    private fun stopPulse() {
        handlerAnimation.removeCallbacks(runnable)
    }

    private var runnable = object : Runnable {
        override fun run() {
            imgAnimation1.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(1000)
                .withEndAction {
                    imgAnimation1.scaleX = 1f
                    imgAnimation1.scaleY = 1f
                    imgAnimation1.alpha = 1f
                }

            imgAnimation2.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(700)
                .withEndAction {
                    imgAnimation2.scaleX = 1f
                    imgAnimation2.scaleY = 1f
                    imgAnimation2.alpha = 1f
                }

            handlerAnimation.postDelayed(this, 1500)
        }
    }
}