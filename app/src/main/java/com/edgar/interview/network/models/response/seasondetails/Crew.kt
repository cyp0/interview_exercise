package com.edgar.interview.network.models.response.seasondetails

data class Crew(
    var adult: Boolean,
    var credit_id: String,
    var department: String,
    var gender: Int,
    var id: Int,
    var job: String,
    var known_for_department: String,
    var name: String,
    var original_name: String,
    var popularity: Double,
    var profile_path: String?
)