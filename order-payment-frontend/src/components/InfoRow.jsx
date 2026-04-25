import { styles } from "../styles/styles";

export default function InfoRow({ label, value }) {
    return (
        <div style={styles.infoRow}>
            <span>{label}</span>
            <span>{value || "-"}</span>
        </div>
    );
}