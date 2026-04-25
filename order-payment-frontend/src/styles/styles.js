export const styles = {
  page: {
    minHeight: "100vh",
    background: "#f1f5f9",
    fontFamily: "Arial, Helvetica, sans-serif",
    color: "#0f172a"
  },
  container: {
    maxWidth: "1200px",
    margin: "0 auto",
    padding: "24px"
  },
  header: {
    background: "#ffffff",
    borderBottom: "1px solid #e2e8f0"
  },
  headerInner: {
    maxWidth: "1200px",
    margin: "0 auto",
    padding: "16px 24px",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    gap: "16px",
    flexWrap: "wrap"
  },
  title: {
    margin: 0,
    fontSize: "28px",
    fontWeight: 700
  },
  subtitle: {
    marginTop: "6px",
    color: "#64748b",
    fontSize: "14px"
  },
  nav: {
    display: "flex",
    gap: "10px",
    flexWrap: "wrap"
  },
  button: {
    border: "none",
    borderRadius: "10px",
    padding: "10px 16px",
    background: "#2563eb",
    color: "#ffffff",
    cursor: "pointer",
    fontSize: "14px",
    fontWeight: 600
  },
  buttonSecondary: {
    border: "1px solid #cbd5e1",
    borderRadius: "10px",
    padding: "10px 16px",
    background: "#ffffff",
    color: "#0f172a",
    cursor: "pointer",
    fontSize: "14px",
    fontWeight: 600
  },
  buttonDanger: {
    border: "none",
    borderRadius: "10px",
    padding: "10px 16px",
    background: "#dc2626",
    color: "#ffffff",
    cursor: "pointer",
    fontSize: "14px",
    fontWeight: 600
  },
  card: {
    background: "#ffffff",
    borderRadius: "18px",
    padding: "20px",
    boxShadow: "0 2px 10px rgba(15, 23, 42, 0.06)"
  },
  sectionTitle: {
    margin: 0,
    fontSize: "24px",
    fontWeight: 700
  },
  muted: {
    color: "#64748b",
    fontSize: "14px"
  },
  grid2: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(320px, 1fr))",
    gap: "24px"
  },
  grid3: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))",
    gap: "24px"
  },
  input: {
    width: "100%",
    padding: "10px 12px",
    borderRadius: "10px",
    border: "1px solid #cbd5e1",
    fontSize: "14px",
    boxSizing: "border-box"
  },
  textarea: {
    width: "100%",
    padding: "12px",
    borderRadius: "10px",
    border: "1px solid #cbd5e1",
    fontSize: "14px",
    boxSizing: "border-box",
    resize: "vertical",
    fontFamily: "Consolas, Monaco, monospace"
  },
  label: {
    display: "block",
    marginTop: "8px",
    marginBottom: "8px",
    fontSize: "14px",
    fontWeight: 600
  },
  blockTitle: {
    margin: "0 0 14px 0",
    fontSize: "18px",
    fontWeight: 700
  },
  infoBox: {
    background: "#f8fafc",
    borderRadius: "14px",
    padding: "16px"
  },
  infoRow: {
    display: "flex",
    justifyContent: "space-between",
    gap: "16px",
    padding: "9px 0",
    borderBottom: "1px solid #e2e8f0",
    fontSize: "14px"
  },
  alert: {
    padding: "12px 14px",
    background: "#fef2f2",
    color: "#b91c1c",
    border: "1px solid #fecaca",
    borderRadius: "10px",
    fontSize: "14px"
  },
  smallCard: {
    background: "#f8fafc",
    borderRadius: "14px",
    padding: "16px"
  },
}