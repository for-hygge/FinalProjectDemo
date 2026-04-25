import { styles } from "../styles/styles";

export default function TextAreaInput({ label, value, onChange, minHeight = 170 }) {
    return (
        <div>
            <label style={styles.label}>{label}</label>
            <textarea 
                style={{ ...styles.textarea, minHeight }}
                value={value}
                onChange={onChange}
            />
        </div>
    );
}