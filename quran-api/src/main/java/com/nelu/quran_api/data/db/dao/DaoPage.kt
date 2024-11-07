package com.nelu.quran_api.data.db.dao

import com.nelu.quran_api.data.model.ModelIndex

/**
 * # DaoPage
 *
 * Interface for accessing page-related data in the Quran. This DAO provides methods
 * to retrieve a list of pages, find a specific page by its number, and locate the page
 * that contains a specific Ayah (verse). Each method is designed to enable structured
 * access to page information in the Quran.
 */
interface DaoPage {

    /**
     * Retrieves a list of all pages in the Quran.
     *
     * @return A list of [ModelIndex] objects representing each page in the Quran.
     */
    fun getPageList(): List<ModelIndex>

    /**
     * Retrieves a specific page by its page number.
     *
     * @param pageId The unique page number in the Quran.
     * @return A [ModelIndex] object for the specified page, or `null` if not found.
     */
    fun getPageByNumber(pageId: Int): ModelIndex?

    /**
     * Finds the page that contains the specified Ayah (verse) ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @return A [ModelIndex] object representing the page containing the given Ayah, or `null` if no page is found.
     */
    fun getPageForAyah(ayahId: Int): ModelIndex?
}