package com.yeterkarakus.maps.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yeterkarakus.maps.R
import com.yeterkarakus.maps.databinding.FragmentDetailsBinding
import com.yeterkarakus.maps.util.Result
import com.yeterkarakus.maps.util.Status
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args : DetailsFragmentArgs by navArgs()
    private lateinit var detailsViewModel : DetailsViewModel


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

        detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]


        args.mArg.let {

            binding.name.text = it.name
            binding.fullAddres.text = it.fullAddress

            if (it.phoneNumber != null){
                binding.phoneNumber.text=it.phoneNumber
                binding.phoneNumber.setOnClickListener {view->
                    val callIntent = Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + it.phoneNumber))
                    try {
                        startActivity(callIntent)

                    }catch (e : Exception){
                        Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    }
                }
            }else {
                binding.phoneNumber.visibility = View.GONE
            }

            if (it.website != null){
                binding.webSite.text=it.website
                binding.webSite.setOnClickListener {view->
                    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.website))
                    try {
                        startActivity(webIntent)

                    }catch (e : Exception){
                        Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    }

                }
            }else {
                binding.webSite.visibility = View.GONE
            }

            it.photoUrl?.let { url ->

                val uri = try {
                    url.toUri()
                } catch (e: Exception) {
                    e.localizedMessage
                }

                Glide.with(requireContext())
                    .load(uri)
                    .fitCenter()
                    .transform(RoundedCorners(15))
                    .error(R.drawable.logo)
                    .into(binding.locationImage)

            }
        }
        observeData()

    }
    private fun observeData(){
         args.mArg.businessId?.let { businessId ->
             detailsViewModel.getReviews(businessId, 5, "tur", "tr")

             detailsViewModel.reviews.observe(viewLifecycleOwner) {

                 when (it.status) {
                     Status.LOADING -> {
                         binding.loadingAnimation.visibility = View.VISIBLE
                         binding.detailsLayout.visibility = View.GONE
                     }

                     Status.SUCCESS -> {
                         binding.loadingAnimation.visibility = View.GONE
                         binding.detailsLayout.visibility = View.VISIBLE

                         it.data?.let { reviews ->
                             val adapter = ReviewsAdapter(reviews.data, requireContext())
                             binding.recyclerView.adapter = adapter
                             binding.recyclerView.layoutManager = LinearLayoutManager(
                                 requireContext(),
                                 LinearLayoutManager.VERTICAL,
                                 false
                             )

                         }
                     }

                     Status.ERROR -> {

                         binding.loadingAnimation.visibility = View.GONE
                         binding.detailsLayout.visibility = View.VISIBLE
                         Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                     }
                 }
             }
         }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}