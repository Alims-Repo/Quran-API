package com.nelu.quran_api.data.repository.base

/**
 * # BasePage
 *
 * Interface for managing access to page-related data in the Quran. This interface provides methods
 * to retrieve a list of all pages, find a specific page by its page number, and locate the page
 * containing a specific Ayah (verse) ID. Each method facilitates structured access to page information.
 */
interface BasePage {

    /**
     * Retrieves a list of all pages in the Quran.
     *
     * @return A list of [String] representations of each page in the Quran.
     */
    fun getPageList(): List<String>

    /**
     * Finds a specific page by its page number.
     *
     * @param pageId The unique page number in the Quran.
     * @return A [String] representing the content or identifier of the specified page, or `null` if not found.
     */
    fun getPageByNumber(pageId: Int): String?

    /**
     * Finds the page that contains the specified Ayah (verse) ID.
     *
     * @param ayahId The unique ID of the Ayah.
     * @return A [String] representing the page containing the given Ayah, or `null` if not found.
     */
    fun getPageForAyah(ayahId: Int): String?
}