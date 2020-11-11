package Observers;

import Models.UserProfileModel;

/**
 * The interface Current user observer.
 */
public interface CurrentUserObserver {

    /**
     * Updates the observer with the new currentUser of the Simulation
     *
     * @param newCurrentUser {@link UserProfileModel} Object representing the new user
     */
    void update(UserProfileModel newCurrentUser);
}
