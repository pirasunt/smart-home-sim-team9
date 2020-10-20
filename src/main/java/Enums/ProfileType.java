package Enums;

/**
 * Enum type representing the different 'privilege' levels that a {@link Models.UserProfileModel}
 * can have. User with a higher level privilege can perform more actions within the simulator
 */
public enum ProfileType {
  /** Adult represents a household member that can perform any action (Family Member) */
  ADULT,
  /** Child represents a household member that has limited actions */
  CHILD,
  /** Guest represents a visitor that was invited by a household member */
  GUEST,
  /** Stranger represents a visitor that was not invited */
  STRANGER
}
