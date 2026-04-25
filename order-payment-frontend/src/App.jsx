import React, { useState } from "react";
import { styles } from "./styles/styles";
import Button from "./components/Button";
import HomePage from "./pages/HomePage";
import OrderPage from "./pages/OrderPage";
import PaymentPage from "./pages/PaymentPage";

export default function App() {
  const [page, setPage] = useState("home");
  const [latestOrder, setLatestOrder] = useState(null);
  const [latestPayment, setLatestPayment] = useState(null);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const handleBuy = (product) => {
    setSelectedProduct(product);
    setPage("orders");
  };

  return (
    <div style={styles.page}>
      <header style={styles.header}>
        <div style={styles.headerInner}>
          <div>
            <h1 style={styles.title}>Order and Payment Management System</h1>
            <div style={styles.subtitle}>Spring Boot + React Project Demo</div>
          </div>

          <div style={styles.nav}>
            <Button onClick={() => setPage("home")} variant={page === "home" ? "primary" : "secondary"}>
              Home
            </Button>
            <Button onClick={() => setPage("orders")} variant={page === "orders" ? "primary" : "secondary"}>
              Orders
            </Button>
            <Button onClick={() => setPage("payments")} variant={page === "payments" ? "primary" : "secondary"}>
              Payments
            </Button>
          </div>
        </div>
      </header>

      <main style={styles.container}>
        {page === "home" && <HomePage onBuy={handleBuy} />}
        {page === "orders" && (
          <OrderPage
            latestOrder={latestOrder}
            setLatestOrder={setLatestOrder}
            latestPayment={latestPayment}
            selectedProduct={selectedProduct}
          />
        )}
        {page === "payments" && (
          <PaymentPage
            latestOrder={latestOrder}
            setLatestOrder={setLatestOrder}
            latestPayment={latestPayment}
            setLatestPayment={setLatestPayment}
          />
        )}
      </main>
    </div>
  );
}