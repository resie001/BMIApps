package lab.chevalier.bmi

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import lab.chevalier.bmi.databinding.FragmentHomeBinding
import java.text.DecimalFormat
import kotlin.math.pow

/**
 * A simple [Fragment] subclass.
 */

@Suppress("DEPRECATION")
@SuppressLint("SetTextI18n")
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private var tinggiBadan = 0.0
    private var beratBadan = 0.0
    private var bmi = 0.0
    private val df = DecimalFormat("#.##")
    private var kategori = 0
    private var kelamin = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = requireActivity().findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnHitung.setOnClickListener { check() }

        binding.btnSaran.setOnClickListener { it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAdviceFragment(kategori)) }

        binding.btnShare.setOnClickListener { share() }
    }

    private fun check(){
        when {
            binding.edBeratBadan.text.isEmpty() || binding.edTinggiBadan.text.isEmpty() || binding.rgKelamin.checkedRadioButtonId == -1 -> {
                Toast.makeText( activity, "Isian tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
            else -> hitung()
        }
    }

    private fun hitung(){

        tinggiBadan = binding.edTinggiBadan.text.toString().toDouble() / 100
        beratBadan = binding.edBeratBadan.text.toString().toDouble()

        bmi = beratBadan / tinggiBadan.pow(2)

        binding.tvBmi.text = "Nilai BMI = ${df.format(bmi)}"

        setVisible()

        if (binding.rgKelamin.checkedRadioButtonId == R.id.rb_pria){
            kelamin = 1
            if (bmi < 20.50){
                setKurus()
            } else if (bmi >= 20.50 && bmi < 27.0){
                setIdeal()
            } else {
                setGemuk()
            }

        } else {
            kelamin = 2
            if (bmi < 18.50){
                setKurus()
            } else if (bmi >=18.50 && bmi < 25.0){
                setIdeal()
            } else {
                setGemuk()
            }
        }
    }

    private fun setVisible(){
        binding.tvBmi.visibility = View.VISIBLE
        binding.tvKategori.visibility = View.VISIBLE
        binding.btnSaran.visibility = View.VISIBLE
        binding.btnShare.visibility = View.VISIBLE
    }

    private fun setKurus(){
        binding.tvKategori.text = "Kurus"
        kategori = 1
        binding.tvKategori.setTextColor(requireActivity().resources.getColor(R.color.kategori_kurus))
    }
    private fun setIdeal(){
        binding.tvKategori.text = "Ideal"
        kategori = 2
        binding.tvKategori.setTextColor(requireActivity().resources.getColor(R.color.kategori_ideal))
    }
    private fun setGemuk(){
        binding.tvKategori.text = "Gemuk"
        kategori = 3
        binding.tvKategori.setTextColor(requireActivity().resources.getColor(R.color.kategori_gemuk))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KATEGORI", kategori)
        outState.putDouble("BMI", bmi)
        outState.putInt("KELAMIN", kelamin)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.getDouble("BMI") != 0.0){
            bmi = savedInstanceState.getDouble("BMI")
            kategori = savedInstanceState.getInt("KATEGORI")
            kelamin = savedInstanceState.getInt("KELAMIN")

            binding.tvBmi.text = "Nilai BMI = ${df.format(bmi)}"

            setVisible()

            if (kelamin == 1){
                if (bmi < 20.50){
                    setKurus()
                } else if (bmi >= 20.50 && bmi < 27.0){
                    setIdeal()
                } else {
                    setGemuk()
                }
            } else {
                if (bmi < 18.50){
                    setKurus()
                } else if (bmi >=18.50 && bmi < 25.0){
                    setIdeal()
                } else {
                    setGemuk()
                }
            }
        }
    }

    private fun share(){

        val tipeBadan = binding.tvKategori.text

        val text = """
            Nama : Ade Resie Muchorobbi Setiawan
            Hasil BMi : $bmi
            Kategori : $tipeBadan
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hasil BMI")
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(intent)
    }

}
