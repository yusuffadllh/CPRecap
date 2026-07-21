# 🤖 SmartFinance AI

## Overview

SmartFinance AI adalah sistem Artificial Intelligence yang membantu pengguna mencatat transaksi keuangan secara otomatis melalui analisis notifikasi dan email transaksi.

Alih-alih mengharuskan pengguna mengisi setiap transaksi secara manual, SmartFinance AI akan:

- Membaca notifikasi transaksi dari aplikasi perbankan dan e-wallet.
- Membaca email transaksi (opsional dengan izin pengguna).
- Mengekstrak informasi penting menggunakan Rule Engine.
- Menggunakan Gemini AI apabila transaksi tidak dapat dikenali.
- Menghasilkan transaksi yang siap disimpan ke database.
- Memberikan kesempatan kepada pengguna untuk mengedit hasil AI sebelum disimpan.

---

# Tujuan

Mengurangi proses input manual dan membuat pencatatan keuangan menjadi lebih cepat, mudah, dan akurat.

---

# Arsitektur AI

```
Notification / Gmail

        │

        ▼

Notification Listener
        &
      Gmail API

        │

        ▼

 Transaction Parser

        │

        ▼

 Regex Extractor

        │
        ▼

 Rule Engine

        │
        ├───────────────┐
        │               │
        │ Confidence ≥ 80%
        ▼               │
 Insert Transaction     │
                        │
            Confidence < 80%
                        ▼

                  Gemini AI

                        ▼

              AI Classification

                        ▼

              Transaction Preview

                        ▼

             Room Database
```

---

# Sistem Hybrid AI

SmartFinance tidak selalu menggunakan AI.

Sebagian besar transaksi diproses menggunakan Rule Engine karena:

- lebih cepat
- tidak membutuhkan internet
- tidak menggunakan token AI
- lebih murah
- lebih konsisten

Gemini AI hanya digunakan apabila Rule Engine gagal menentukan kategori atau merchant.

---

# Modul AI

## 1. Notification Listener

Membaca notifikasi secara real-time menggunakan NotificationListenerService.

Contoh aplikasi yang didukung:

- BCA Mobile
- myBCA
- Livin' by Mandiri
- BRImo
- BNI Mobile
- SeaBank
- Jenius
- blu
- NeoBank
- DANA
- OVO
- GoPay
- ShopeePay
- LinkAja

AI hanya memproses notifikasi transaksi.

---

## 2. Gmail Reader

Dengan izin pengguna, SmartFinance dapat membaca email transaksi melalui Gmail API.

Contoh:

- Bukti transfer
- Pembayaran QRIS
- Invoice
- E-commerce
- Langganan digital

Email pribadi tidak akan diproses.

---

## 3. Regex Extractor

Rule pertama adalah mengambil informasi penting menggunakan Regular Expression.

Data yang diekstrak:

- Nominal
- Tanggal
- Jam
- Merchant
- Bank
- Nomor referensi
- Jenis transaksi

Contoh regex:

Nominal

```
Rp\s?([\d.,]+)
```

Jam

```
\d{2}:\d{2}
```

Tanggal

```
\d{2}/\d{2}/\d{4}
```

---

## 4. Rule Engine

Rule Engine melakukan klasifikasi menggunakan keyword dan dictionary.

Contoh:

```
Mixue
↓
Food
```

```
KFC
↓
Food
```

```
Starbucks
↓
Food
```

```
GrabFood
↓
Food
```

```
Pertamina
↓
Transport
```

```
Grab
↓
Transport
```

```
Gojek
↓
Transport
```

```
Tokopedia
↓
Shopping
```

```
Shopee
↓
Shopping
```

```
Steam
↓
Entertainment
```

```
Spotify
↓
Subscription
```

```
Netflix
↓
Subscription
```

```
PLN
↓
Bills
```

```
BPJS
↓
Health
```

Jika merchant ditemukan pada dictionary maka AI tidak diperlukan.

---

## 5. Gemini AI

Gemini digunakan hanya ketika Rule Engine gagal mengenali transaksi.

Contoh:

Input

```
Pembayaran berhasil

Rp89.000

PT XYZ DIGITAL INDONESIA
```

Rule Engine:

```
Unknown Merchant
```

Gemini menerima prompt:

```
Merchant:
PT XYZ DIGITAL INDONESIA

Nominal:
89000

Prediksi kategori transaksi.
Jawab hanya satu kategori.
```

Output:

```
Subscription
```

---

## 6. AI Confidence Score

Setiap hasil klasifikasi memiliki tingkat keyakinan.

Contoh:

```
Kategori

Food

Confidence

98%
```

```
Kategori

Shopping

Confidence

92%
```

```
Kategori

Unknown

Confidence

35%
```

Apabila confidence rendah (<80%), pengguna akan diminta memilih kategori secara manual.

---

# Auto Detection

AI mencoba mendeteksi:

- Jenis transaksi
- Nominal
- Merchant
- Kategori
- Metode pembayaran
- Waktu transaksi
- Catatan transaksi

---

# Auto Income Detection

Contoh:

```
Transfer Masuk Rp5.000.000
```

↓

Income

Kategori

Salary

---

```
Cashback Rp20.000
```

↓

Income

Kategori

Cashback

---

```
Refund Tokopedia
```

↓

Income

Kategori

Refund

---

# Auto Expense Detection

```
OVO

Rp35.000

Mixue
```

↓

Expense

Kategori

Food

---

```
Pertamina

Rp150.000
```

↓

Expense

Kategori

Transport

---

```
Netflix

Rp65.000
```

↓

Expense

Kategori

Subscription

---

# Manual Input

Selain menggunakan AI, pengguna tetap dapat:

- Menambah transaksi secara manual.
- Mengubah kategori hasil AI.
- Mengubah nominal.
- Mengubah tanggal.
- Mengubah catatan.
- Menghapus transaksi.

AI hanya membantu, keputusan akhir tetap berada pada pengguna.

---

# Privacy

SmartFinance hanya memproses data yang telah diizinkan oleh pengguna.

AI tidak membaca:

- OTP
- Password
- PIN
- Chat
- Email pribadi yang tidak berkaitan dengan transaksi

Semua proses digunakan hanya untuk membantu pencatatan keuangan.

---

# Teknologi

Android

- Kotlin
- Jetpack Compose
- Hilt
- Room Database
- DataStore
- NotificationListenerService
- Credential Manager
- Firebase Authentication

Artificial Intelligence

- Google Gemini API
- Prompt Engineering
- Rule Engine
- Regex Parser
- Merchant Dictionary
- Confidence Scoring

Cloud

- Firebase Authentication
- Gmail API (planned)

---

# Status Implementasi

| Feature | Status |
|----------|--------|
| Notification Listener | ⏳ Planned |
| Gmail Reader | ⏳ Planned |
| Regex Extractor | ⏳ Planned |
| Rule Engine | ⏳ Planned |
| Merchant Dictionary | ⏳ Planned |
| Gemini Integration | ⏳ Planned |
| AI Confidence | ⏳ Planned |
| Transaction Preview | ⏳ Planned |
| Auto Category | ⏳ Planned |
| Manual Override | ⏳ Planned |
| Room Integration | ⏳ Planned |

---

# Future Improvements

- Pengingat tagihan otomatis.
- Prediksi pengeluaran bulanan.
- Deteksi transaksi duplikat.
- Dukungan lebih banyak bank dan e-wallet.
