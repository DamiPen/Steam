package com.github.damipen.steamapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.github.damipen.steam.Steam
import kotlinx.android.synthetic.main.fragment_steam_community.webview

class SteamCommunityFragment : Fragment() {

    private val Url = "https://steamcommunity.com/search/users/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_steam_community, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.settings.javaScriptEnabled = true

        webview.webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {

                return super.shouldInterceptRequest(view, request)
            }

            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                return super.shouldInterceptRequest(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                Log.d("SteamCommunityFragment", "url [${request?.url}]")
                val path = request?.url?.path ?: return true
                return when {
                    path.startsWith("/profiles/") -> {

                        true
                    }
                    path.startsWith("/id/") -> {

                        true
                    }
                    else -> super.shouldOverrideUrlLoading(view, request)
                }
            }

        }
        webview.loadUrl(Url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}

class CommunityViewModel(
    val steam: Steam
) : ViewModel() {

    fun startViewForProfile(profile: String) {

    }

    fun fetchSteamIdFromVanityUrl(url: String) {

    }

}
