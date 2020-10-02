package com.example.omdb.util

import org.json.JSONObject

object FacebookParse {

    fun getFirstName(response: JSONObject): String? {
        return response["first_name"] as? String
    }

    fun getLastName(response: JSONObject): String? {
        return response["last_name"] as? String
    }

    fun getEmail(response: JSONObject): String? {
        return response["email"] as? String
    }

    fun getProfileUrl(response: JSONObject): String? {
        val id = response["id"] as? String ?: return null
        return "https://graph.facebook.com/$id/picture?height=500"
    }

}