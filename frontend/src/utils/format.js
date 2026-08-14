// 數字加上千分位逗號，例如 1234567 -> "1,234,567"、1234.5 -> "1,234.5"
export function formatNumber(value) {
  if (value == null) return value;
  return Number(value).toLocaleString('zh-TW');
}
