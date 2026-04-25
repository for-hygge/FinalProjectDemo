import React, { useState } from "react";
import { request, formatMoney } from "../services/request";
import { styles } from "../styles/styles";
import Button from "../components/Button";
import InfoRow from "../components/InfoRow";
import TextInput from "../components/TextInput";

export default function PaymentPage({ latestOrder, setLatestOrder, latestPayment, setLatestPayment }) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [paymentId, setPaymentId] = useState(latestPayment?.paymentId || "");
  const [orderId, setOrderId] = useState(latestOrder?.orderId || "");
  const [userId, setUserId] = useState(String(latestOrder?.userId || 10001));
  const [amount, setAmount] = useState(String(latestOrder?.totalAmount || 199.99));
  const [paymentMethod, setPaymentMethod] = useState(latestPayment?.paymentMethod || "CARD");
  const [refundAmount, setRefundAmount] = useState("50");
  const [refundReason, setRefundReason] = useState("After-sales refund request");
  const [cancelReason, setCancelReason] = useState("Payment cancelled by user");
  const [paymentDetail, setPaymentDetail] = useState(null);
  const [refundResult, setRefundResult] = useState(null);

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

  const createPayment = async () => {
    await run(async () => {
      const data = await request(`/v1/payments`, {
        method: "POST",
        body: JSON.stringify({
          orderId,
          userId: Number(userId),
          amount: Number(amount),
          paymentMethod,
        }),
      });

      setLatestPayment(data);
      setPaymentDetail(data);
      if (data?.paymentId) setPaymentId(data.paymentId);

      setLatestOrder((prev) => ({
        ...(prev || {}),
        orderId: prev?.orderId || orderId,
        userId: prev?.userId || Number(userId),
        totalAmount: prev?.totalAmount || Number(amount),
        paymentId: data?.paymentId,
        paymentStatus: data?.status,
      }));
    });
  };

  const queryPayment = async () => {
    await run(async () => {
      const data = await request(`/v1/payments/${paymentId}`);
      setPaymentDetail(data);
    });
  };

  const cancelPayment = async () => {
    await run(async () => {
      const data = await request(`/v1/payments/${paymentId}/cancel`, {
        method: "POST",
        body: JSON.stringify({ paymentId, reason: cancelReason }),
      });

      setLatestPayment(data);
      setPaymentDetail(data);

      setLatestOrder((prev) => ({
        ...(prev || {}),
        orderId: prev?.orderId || data?.orderId || orderId,
        userId: prev?.userId || data?.userId || Number(userId),
        totalAmount: prev?.totalAmount || data?.amount || Number(amount),
        paymentId: data?.paymentId,
        status: "PAYMENT_CANCELLED",
      }));
    });
  };

  const refundPayment = async () => {
    await run(async () => {
      const data = await request(`/v1/payments/${paymentId}/refund`, {
        method: "POST",
        body: JSON.stringify({
          paymentId,
          refundAmount: Number(refundAmount),
          reason: refundReason,
        }),
      });

      setRefundResult(data);

      setLatestPayment((prev) => ({
        ...(prev || {}),
        paymentId: prev?.paymentId || paymentId,
        orderId: prev?.orderId || orderId,
        userId: prev?.userId || Number(userId),
        amount: prev?.amount || Number(amount),
        paymentMethod: prev?.paymentMethod || paymentMethod,
        status: "REFUNDED",
      }));

      setPaymentDetail((prev) => ({
        ...(prev || {}),
        paymentId: prev?.paymentId || paymentId,
        orderId: prev?.orderId || orderId,
        userId: prev?.userId || Number(userId),
        amount: prev?.amount || Number(amount),
        paymentMethod: prev?.paymentMethod || paymentMethod,
        status: "REFUNDED",
      }));

      setLatestOrder((prev) => ({
        ...(prev || {}),
        orderId: prev?.orderId || orderId,
        userId: prev?.userId || Number(userId),
        totalAmount: prev?.totalAmount || Number(amount),
        paymentId: prev?.paymentId || paymentId,
        status: "REFUNDED",
      }));
    });
  };

  return (
    <div style={{ display: "grid", gap: "24px" }}>
      <div style={styles.grid2}>
        <div style={styles.card}>
          <h2 style={styles.blockTitle}>Pay Order</h2>

          <div style={{ display: "grid", gap: "16px" }}>
            <div style={styles.infoBox}>
              <div style={{ fontWeight: 700, marginBottom: "8px" }}>🧾 Pending Order</div>
              <InfoRow label="Order ID" value={latestOrder?.orderId || orderId} />
              <InfoRow label="Order Amount" value={formatMoney(latestOrder?.totalAmount || amount)} />
              <InfoRow label="User ID" value={latestOrder?.userId || userId} />
            </div>

            <TextInput label="Order ID" value={orderId} onChange={(e) => setOrderId(e.target.value)} />
            <TextInput label="User ID" value={userId} onChange={(e) => setUserId(e.target.value)} />
            <TextInput label="Payment Amount" value={amount} onChange={(e) => setAmount(e.target.value)} />
            <TextInput label="Payment Method" value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)} />

            <Button onClick={createPayment} disabled={loading}>
              Create Payment
            </Button>

            {error ? <div style={styles.alert}>{error}</div> : null}
          </div>
        </div>

        <div style={styles.card}>
          <h2 style={styles.blockTitle}>Payment Result</h2>

          <InfoRow label="Payment ID" value={latestPayment?.paymentId || paymentDetail?.paymentId} />
          <InfoRow label="Order ID" value={paymentDetail?.orderId || orderId} />
          <InfoRow label="Status" value={paymentDetail?.status || latestPayment?.status} />
          <InfoRow label="Amount" value={formatMoney(paymentDetail?.amount || latestPayment?.amount || amount)} />
          <InfoRow label="Payment Method" value={paymentDetail?.paymentMethod || latestPayment?.paymentMethod || paymentMethod} />
          <InfoRow label="Created At" value={paymentDetail?.createdAt || latestPayment?.createdAt} />
        </div>
      </div>

      <div style={styles.card}>
        <h2 style={styles.blockTitle}>Payment Management</h2>

        <div style={styles.grid3}>
          <div style={styles.smallCard}>
            <h3 style={{ marginTop: 0 }}>Query Payment</h3>
            <TextInput label="Payment ID" value={paymentId} onChange={(e) => setPaymentId(e.target.value)} />

            <div style={{ marginTop: "10px" }}>
              <Button onClick={queryPayment} disabled={loading}>
                Load Payment
              </Button>
            </div>
            <div>
              <InfoRow label="Payment ID" value={paymentDetail?.paymentId} />
              <InfoRow label="Payment Sattus" value={paymentDetail?.status} />
              <InfoRow label="Amount" value={formatMoney(paymentDetail?.amount)} />
              <InfoRow label="Payment Method" value={paymentDetail?.paymentMethod} />
            </div>
          </div>

          <div style={styles.smallCard}>
            <h3 style={{ marginTop: 0 }}>Cancel Payment</h3>
            <TextInput label="Payment ID" value={paymentId} onChange={(e) => setPaymentId(e.target.value)} />
            <TextInput label="Cancel Reason" value={cancelReason} onChange={(e) => setCancelReason(e.target.value)} />
            <div style={{ marginTop: "10px" }}>
              <Button onClick={cancelPayment} disabled={loading} variant="danger">
                Cancel Payment
              </Button>
            </div>
          </div>

          <div style={styles.smallCard}>
            <h3 style={{ marginTop: 0 }}>Refund Payment</h3>
            <TextInput label="Payment ID" value={paymentId} onChange={(e) => setPaymentId(e.target.value)} />
            <TextInput label="Refund Amount" value={refundAmount} onChange={(e) => setRefundAmount(e.target.value)} />
            <TextInput label="Refund Reason" value={refundReason} onChange={(e) => setRefundReason(e.target.value)} />
            <div style={{ marginTop: "10px" }}>
              <Button onClick={refundPayment} disabled={loading}>
                Submit Refund
              </Button>
            </div>
            <InfoRow label="Refund ID" value={refundResult?.refundId} />
            <InfoRow label="Refund Status" value={refundResult?.status} />
            <InfoRow label="Refund Amount" value={formatMoney(refundResult?.refundAmount)} />
          </div>
        </div>
      </div>
    </div>
  );
}