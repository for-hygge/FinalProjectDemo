export const ORDER_API = "http://localhost:8080";
export const PAYMENT_API = "http://localhost:8081";

export async function request(path, options = {}) {
    const base = path.startsWith("/v1/orders") ? ORDER_API : PAYMENT_API;

    const response = await fetch(`${base}${path}`, {
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {})
        },
        ...options
    });

    const text = await response.text();
    let data;
    try {
        data = text ? JSON.parse(text) : null;
    } catch {
        data = text;
    }

    if (!response.ok) {
        throw new Error(typeof data === "string" ? data : JSON.stringify(data));
    }

    return data;
}

export function safeJsonParse(text, fallback) {
    try {
        return JSON.parse(text);
    } catch {
        return fallback;
    }
}

export function formatMoney(value) {
    if (!value) return "-";
    return `$${value}`;
}