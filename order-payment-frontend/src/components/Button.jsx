import { styles } from "../styles/styles";

export default function Button({
  children,
  onClick,
  disabled = false,
  variant = "primary",
  type = "button",
}) {
  const buttonStyle =
    variant === "danger"
      ? styles.buttonDanger
      : variant === "secondary"
      ? styles.buttonSecondary
      : styles.button;

  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      style={{
        ...buttonStyle,
        opacity: disabled ? 0.6 : 1,
      }}
    >
      {disabled ? "Processing..." : children}
    </button>
  );
}