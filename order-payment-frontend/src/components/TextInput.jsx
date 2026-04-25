import { styles } from "../styles/styles";

export default function TextInput({ label, value, onChange }) {
  return (
    <div>
      <label style={styles.label}>{label}</label>
      <input style={styles.input} value={value} onChange={onChange} />
    </div>
  );
}