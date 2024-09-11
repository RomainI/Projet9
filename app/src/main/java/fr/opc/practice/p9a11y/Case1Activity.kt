package fr.opc.practice.p9a11y

import android.graphics.Rect
import android.os.Bundle
import android.view.TouchDelegate
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.opc.practice.p9a11y.databinding.ActivityCase1Binding

class Case1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCase1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCase1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var quantity = 0

        binding.quantityText.text = "$quantity"
        binding.addButton.setOnClickListener {
            quantity++
            binding.quantityText.text = "$quantity"
            binding.quantityText.contentDescription = "Quantité : $quantity"
            binding.quantityText.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        }
       var delegateArea = Rect()
        binding.addButton.getHitRect(delegateArea)

        delegateArea.top-=70
        delegateArea.bottom+=70
        delegateArea.left-=70
        delegateArea.right+=70

        val touchDelegate = TouchDelegate(delegateArea, binding.addButton)
        if (View::class.java.isInstance(binding.addButton.parent)) {
            (binding.addButton.parent as View).touchDelegate = touchDelegate
        }

        var delegateAreaRemove = Rect()
        binding.removeButton.getHitRect(delegateAreaRemove)

        delegateAreaRemove.top-=70
        delegateAreaRemove.bottom+=70
        delegateAreaRemove.left-=70
        delegateAreaRemove.right+=70

        val touchDelegateRemove = TouchDelegate(delegateAreaRemove, binding.removeButton)
        if (View::class.java.isInstance(binding.removeButton.parent)) {
            (binding.removeButton.parent as View).touchDelegate = touchDelegateRemove
        }


        binding.removeButton.setOnClickListener {
            if (quantity > 0) {
                quantity--
                binding.quantityText.text = "$quantity"
                binding.quantityText.contentDescription = "Quantité : $quantity"
                binding.quantityText.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            } else {
                Toast.makeText(this, getString(R.string.impossible_d_avoir_une_quantit_n_gative), Toast.LENGTH_SHORT)
                    .show()
                binding.quantityText.contentDescription = getString(R.string.impossible_d_avoir_une_quantit_n_gative)
                binding.quantityText.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
            }
        }
    }
}
