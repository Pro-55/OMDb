package com.example.omdb.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helps save and restore Fragment state outside of an Activity's typical
 * save/restore state lifecycle.
 * ref: https://github.com/bherbst/Fragment-State-Sample/blob/master/app/src/main/java/com/bryanherbst/fragmentstate/FragmentStateHelper.kt
 */
@Singleton
class FragmentStateHelper @Inject constructor() {

    private val fragmentSavedStates = mutableMapOf<String, Fragment.SavedState?>()

    /**
     * Restore a Fragment's previously saved state via [saveState]
     */
    fun restoreState(fragment: Fragment) {
        val key = fragment::class.java.simpleName
        fragmentSavedStates[key]?.let { savedState ->
            // We can't set the initial saved state if the Fragment is already added
            // to a FragmentManager, since it would then already be created.
            if (!fragment.isAdded) {
                fragment.setInitialSavedState(savedState)
            }
        }
    }

    /**
     * Save a Fragment's state for later restoration via [restoreState]
     */
    fun saveState(fragmentManager: FragmentManager, fragment: Fragment) {
        val key = fragment::class.java.simpleName
        // We can't save the state of a Fragment that isn't added to a FragmentManager.
        if (fragment.isAdded || fragment.isRemoving) {
            fragmentSavedStates[key] = fragmentManager.saveFragmentInstanceState(fragment)
        }
    }

}