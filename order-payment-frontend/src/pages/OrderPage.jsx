import React, { useMemo, useState } from "react";
import { request, safeJsonParse, formatMoney } from "../services/request";
import { styles } from "../styles/styles";
import Button from "../components/Button";
import InfoRow from "../components/InfoRow";
import TextInput from "../components/TextInput";
import TextAreaInput from "../components/TextAreaInput";

export default function OrderPage({ latestOrder, setLatestOrder, latestPayment, selectedProduct }) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [orderList, setOrderList] = useState([]);
  const [orderDetail, setOrderDetail] = useState(null);
  const [orderPayment, setOrderPayment] = useState(null);
  const [userId, setUserId] = useState(String(latestOrder?.userId || 10001));
  const [orderId, setOrderId] = useState(latestOrder?.orderId || "");
  const [cancelReason, setCancelReason] = useState("Customer requested cancellation");

  const defaultItems = useMemo(
    () =>
      JSON.stringify(
        [
          {
            productId: selectedProduct?.id || 20001,
            productName: selectedProduct?.name || "Wireless Bluetooth Earbuds",
            price: selectedProduct?.price || 199.99,
            quantity: 1,
          },
        ],
        null,
        2
      ),
    [selectedProduct]
  );

  const [itemRequestsText, setItemRequestsText] = useState(defaultItems);

  const [shippingAddressText, setShippingAddressText] = useState(
    JSON.stringify(
      {
        customerName: "John Doe",
        phone: "13800138000",
        email: "john@example.com",
        addressLine1: "100 W St",
        addressLine2: "Building A, Room 808",
        city: "Plano",
        state: "TX",
        country: "USA",
      },
      null,
      2
    )
  );

  const run = async (fn) => {
    setLoading(true);
    setError("");
    try {
      await fn();
    } catch (e) {
      setError(e.message || "Request failed");
    } finally {
      setLoading(false);
    }
  };

  const createOrder = async () => {
    await run(async () => {
      const data = await request(`/v1/orders`, {
        method: "POST",
        body: JSON.stringify({
          userId: Number(userId),
          itemRequests: safeJsonParse(itemRequestsText, []),
          shippingAddress: safeJsonParse(shippingAddressText, {}),
        }),
      });
      setLatestOrder(data);
      if (data?.orderId) setOrderId(data.orderId);
    });
  };

  const queryOrders = async () => {
    await run(async () => {
      const data = await request(`/v1/orders?userId=${userId}`);
      setOrderList(Array.isArray(data) ? data : []);
    });
  };

  const queryOrderDetail = async () => {
    await run(async () => {
      const data = await request(`/v1/orders/${orderId}`);
      setOrderDetail(data);
    });
  };

  const queryOrderPayment = async () => {
    await run(async () => {
      const data = await request(`/v1/orders/${orderId}/payment`);
      setOrderPayment(data);
    });
  };

  const cancelOrder = async () => {
    await run(async () => {
      const data = await request(`/v1/orders/${orderId}/cancel`, {
        method: "POST",
        body: JSON.stringify({ orderId, reason: cancelReason }),
      });
      setLatestOrder(data);
      setOrderDetail(null);
    });
  };

  return (
    <div style={{ display: "grid", gap: "24px" }}>
      <div style={styles.grid2}>
        <div style={styles.card}>
          <h2 style={styles.blockTitle}>Create Order</h2>
          <div style={{ display: "grid", gap: "16px" }}>
            <TextInput label="User ID" value={userId} onChange={(e) => setUserId(e.target.value)} />
            <TextAreaInput label="Item Requests (JSON)" value={itemRequestsText} onChange={(e) => setItemRequestsText(e.target.value)} minHeight={170} />
            <TextAreaInput label="Shipping Address (JSON)" value={shippingAddressText} onChange={(e) => setShippingAddressText(e.target.value)} minHeight={190} />
            <Button onClick={createOrder} disabled={loading}>Submit Order</Button>
            {error ? <div style={styles.alert}>{error}</div> : null}
          </div>
        </div>

        <div style={styles.card}>
          <h2 style={styles.blockTitle}>Current Order Summary</h2>
          <InfoRow label="Latest Order ID" value={latestOrder?.orderId} />
          <InfoRow label="User ID" value={latestOrder?.userId || userId} />
          <InfoRow label="Order Status" value={latestOrder?.status} />
          <InfoRow label="Order Amount" value={formatMoney(latestOrder?.totalAmount)} />
          <InfoRow label="Related Payment ID" value={latestOrder?.paymentId || latestPayment?.paymentId} />
        </div>
      </div>

      <div style={styles.card}>
        <h2 style={styles.blockTitle}>Order Management</h2>
        <div style={styles.grid3}>

          <div style={styles.smallCard}>
            <h3 style={{ marginTop: 0 }}>Order List</h3>
            <TextInput label="User ID" value={userId} onChange={(e) => setUserId(e.target.value)} />
            <div style={{ marginTop: "10px" }}>
              <Button onClick={queryOrders} disabled={loading}>Load Orders</Button>
            </div>

            <div style={{ display: "grid", gap: "10px", marginTop: "16px" }}>
              {orderList.length === 0 ? (
                <p style={styles.infoRow}>No data</p>
              ) : (
                orderList.map((item, index) => (
                  <div key={index}>
                    <div style={styles.infoRow}>Order: {item.orderId}</div>
                    <div style={styles.infoRow}>Status: {item.status}</div>
                    <div style={styles.infoRow}>Amount: {formatMoney(item.totalAmount)}</div>
                    <div style={styles.infoRow}>Payment ID: {item.paymentId}</div>
                  </div>
                ))
              )}
            </div>
          </div>

          <div style={styles.smallCard}>
            <h3 style={{ marginTop: 0 }}>Order Detail / Payment Info</h3>
            <TextInput label="Order ID" value={orderId} onChange={(e) => setOrderId(e.target.value)} />
            <div style={{ marginTop: "10px" }}>
              <Button onClick={queryOrderDetail} disabled={loading}>Load Detail</Button>
            </div>

            <InfoRow label="Order ID" value={orderDetail?.orderId} />
            <InfoRow label="User ID" value={orderDetail?.userId} />
            <InfoRow label="Status" value={orderDetail?.status} />
            <InfoRow label="Amount" value={formatMoney(orderDetail?.totalAmount)} />

            <div style={{ marginTop: "16px" }}>
              <Button onClick={queryOrderPayment} disabled={loading}>Load Payment</Button>
            </div>
            <InfoRow label="Payment ID" value={orderPayment?.paymentId} />
            <InfoRow label="Payment Status" value={orderPayment?.paymentStatus} />
            <InfoRow label="Payment Method" value={orderPayment?.paymentMethod} />
          </div>

          <div style={styles.smallCard}>
            <h3 style={{ marginTop: 0 }}>Cancel Order</h3>
            <TextInput label="Order ID" value={orderId} onChange={(e) => setOrderId(e.target.value)} />
            <TextInput label="Cancel Reason" value={cancelReason} onChange={(e) => setCancelReason(e.target.value)} />
            <div style={{ marginTop: "10px" }}>
              <Button onClick={cancelOrder} disabled={loading} variant="danger">Cancel Order</Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}