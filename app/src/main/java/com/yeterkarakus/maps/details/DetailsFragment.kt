package com.yeterkarakus.maps.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yeterkarakus.maps.databinding.FragmentDetailsBinding
import java.lang.Exception

class DetailsFragment : Fragment() {
    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    val args : DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        args.let {

            binding.name.text = it.mArg.name
            binding.fullAddres.text = it.mArg.fullAddress

            if (it.mArg.phoneNumber != null){
                binding.phoneNumber.text=it.mArg.phoneNumber
            }else {
                binding.phoneNumber.visibility = View.GONE
            }

            if (it.mArg.website != null){
                binding.webSite.text=it.mArg.website
            }else {
                binding.webSite.visibility = View.GONE
            }

            it.mArg.photoUrl?.let {url->
                val uri = try {
                    url.toUri()
                }catch (e : Exception){
                    e.localizedMessage
                }
                Glide.with(requireContext()).load(uri).fitCenter().into(binding.locationImage)
            }

        }
    }



}