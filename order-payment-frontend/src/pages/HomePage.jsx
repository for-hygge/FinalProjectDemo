import React from "react";
import { styles } from "../styles/styles";
import { mockProducts } from "../data/product";
import Card from "../components/Card";

export default function HomePage({ onBuy }) {
  return (
    <div style={{ display: "flex", flexDirection: "column", gap: "24px" }}>
      <div style={styles.card}>
        <h2 style={styles.blockTitle}>System Features</h2>
        <div style={{ display: "grid", gap: "14px" }}>
          <div style={styles.infoBox}>
            <div style={{ fontWeight: 700, marginBottom: "8px" }}>
              📦 Order Management
            </div>
            <div style={styles.muted}>
              Create orders, query orders, view order details, check payment
              information, and cancel orders.
            </div>
          </div>

          <div style={styles.infoBox}>
            <div style={{ fontWeight: 700, marginBottom: "8px" }}>
              💳 Payment Management
            </div>
            <div style={styles.muted}>
              Create payments, query payments, cancel payments, and process refunds.
            </div>
          </div>
        </div>
      </div>

      <div>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            gap: "16px",
            marginBottom: "16px",
            flexWrap: "wrap",
          }}
        >
          <h2 style={styles.sectionTitle}>Product List</h2>
          <div style={styles.muted}>
            Select a product to automatically proceed to the order page
          </div>
        </div>

        <div style={styles.grid3}>
          {mockProducts.map((product) => (
            <Card key={product.id} product={product} onBuy={onBuy} />
          ))}
        </div>
      </div>
    </div>
  );
}