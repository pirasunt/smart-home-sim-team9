package Custom;

/**
 * This Exception is thrown when a modification is attempted on a UserProfile Object that does not
 * exist in the {@link Models.EnvironmentModel} Object.
 *
 * <p>This check is done by checking the UserProfile's UUID against the UUIDs of all the profiles in
 * {@link Models.EnvironmentModel}
 */
public class NonExistantUserProfileException extends Exception {
  /**
   * Constructs a new exception with the specified detail message. The cause is not initialized, and
   * may subsequently be initialized by a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for later retrieval by the
   *     {@link #getMessage()} method.
   */
  public NonExistantUserProfileException(String message) {
    super(message);
  }

  /**
   * Constructs a new exception with {@code null} as its detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause}.
   */
  public NonExistantUserProfileException() {
    super("The provided UserProfile's UUID is does not exist or has been deleted.");
  }
}
