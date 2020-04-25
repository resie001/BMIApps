package lab.chevalier.bmi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import lab.chevalier.bmi.databinding.FragmentAdviceBinding

/**
 * A simple [Fragment] subclass.
 */
class AdviceFragment : Fragment() {

    val args : AdviceFragmentArgs by navArgs()
    private lateinit var binding: FragmentAdviceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_advice, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when(args.kategori){
            1 -> setKurus()
            2 -> setIdeal()
            else -> setGemuk()
        }
    }

    private fun setKurus(){
        binding.imgSaran.setImageResource(R.drawable.kurus)
        binding.tvSaran.text = requireActivity().getString(R.string.saran_kurus)
    }

    private fun setIdeal(){
        binding.imgSaran.setImageResource(R.drawable.ideal)
        binding.tvSaran.text = requireActivity().getString(R.string.saran_ideal)
    }

    private fun setGemuk(){
        binding.imgSaran.setImageResource(R.drawable.gemuk)
        binding.tvSaran.text = requireActivity().getString(R.string.saran_gemuk)
    }

}
