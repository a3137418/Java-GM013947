/**
 * 解碼 JWT 的 payload 部分
 * 注意：這裡只是解碼（base64 解碼），沒有驗證簽章
 * 前端解碼只是為了拿 username/role 顯示用，不能拿來做真正的權限驗證
 * 真正的驗證還是要靠後端 JwtAuthenticationFilter
 */

export function decodeToken(token){
    // JWT 格式是 header.payload.signature，用 . 分隔三段，取第二段(payload)
    const payload = token.split('.')[1];

    // JWT 用的是 base64url 編碼，跟一般 base64 差在 -/_ 這兩個字元
    // 要先換成標準 base64 用的 +/ 字元，atob() 才能正確解碼
    const base64 = payload.replace(/-/g, '+').replace(/_/g , '/');

    // atob() 是瀏覽器內建的 base64 解碼函式，解出來是 JSON 字串
    // 再用 JSON.parse 轉成物件，裡面會有 sub(username)、role、iat、exp 等欄位
    return JSON.parse(atob(base64));
}