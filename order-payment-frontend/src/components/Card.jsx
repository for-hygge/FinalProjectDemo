import { styles } from "../styles/styles";
import Button from "./Button";

export default function Card({ product, onBuy }) {
  return (
    <div
      style={{
        ...styles.card,
        display: "flex",
        flexDirection: "column",
        height: "100%",
      }}
    >
      <div
        style={{
          width: "100%",
          height: "160px",
          overflow: "hidden",
          borderRadius: "14px",
          marginBottom: "16px"
        }}
      >
        <img
          src={product.image}
          alt={product.name}
          style={{
            width: "100%",
            height: "100%",
            objectFit: "cover",
            display: "block"
          }}
        />
      </div>

      <h3
        style={{
          margin: "0 0 8px 0",
          fontSize: "20px",
          minHeight: "56px"
        }}
      >
        {product.name}
      </h3>

      <p
        style={{
          ...styles.muted,
          marginTop: 0,
          lineHeight: 1.6,
          minHeight: "120px",
          flexGrow: 1
        }}
      >
        {product.description}
      </p>

      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginTop: "16px",
          gap: "12px",
          flexShrink: 0
        }}
      >
        <span style={{ fontSize: "22px", fontWeight: 700 }}>
          ${product.price}
        </span>

        <Button onClick={() => onBuy(product)}>Buy It Now!</Button>
      </div>
    </div>
  );
}