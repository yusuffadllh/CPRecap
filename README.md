# SmartFinance (CPRecap)

Aplikasi Android manajemen keuangan pribadi berbasis **Kotlin + Jetpack Compose** dengan deteksi transaksi otomatis berbantuan AI.

> SmartFinance adalah **personal finance**, bukan mobile banking. Aplikasi tidak melakukan transfer, pembayaran, top up, maupun menyimpan PIN/OTP.

---

## ✨ Fitur

- **Autentikasi** — Google Sign-In & Email/Password (Firebase Authentication), auto-login selama token valid.
- **Dashboard** — Saldo, income vs expense, quick action, ringkasan budget, chart analytics, transaksi terbaru.
- **Transaksi** — Create, update, delete, search, filter, sort (Income & Expense).
- **Deteksi Otomatis (AI)** — Membaca notifikasi bank & email Gmail (readonly), lalu memprediksi merchant, nominal, kategori, dan tipe transaksi. Semua hasil dapat dikonfirmasi user terlebih dahulu.
- **Budget** — Anggaran per kategori dengan progress & peringatan bila melebihi.
- **Analytics** — Income vs expense, breakdown kategori, tren bulanan/mingguan.
- **Backup** — Ekspor/impor data.
- **Dark Mode** & Material 3.

---

## 🏗️ Arsitektur

Clean Architecture + MVVM + Repository Pattern:

```
Presentation (Compose)
      ↓
ViewModel (state)
      ↓
UseCase / Repository (interface)
      ↓
Repository Impl
      ↓
Local (Room / DataStore) · Remote (Firebase) · AI
```

### Tech Stack
| Kategori | Teknologi |
|----------|-----------|
| Bahasa | Kotlin |
| UI | Jetpack Compose, Material 3 |
| DI | Hilt |
| Database | Room |
| Preferences | DataStore |
| Cloud | Firebase Authentication |
| AI | OpenAI-compatible / Gemini (auto-detect) |
| Background | WorkManager |
| Navigasi | Navigation Compose |

### Requirement
- Android Studio (terbaru disarankan)
- **minSdk 26**, **targetSdk 35**, **compileSdk 35**
- JDK 11

---

## 🚀 Setup

### 1. Clone

```bash
git clone https://github.com/yusuffadllh/CPRecap.git
cd CPRecap
```

### 2. Konfigurasi Firebase (`google-services.json`) — WAJIB

> [!IMPORTANT]
> File `app/google-services.json` **tidak** disertakan di repo ini (berisi konfigurasi Firebase project & sengaja di-ignore untuk keamanan). Anda **harus** menyediakannya sendiri, atau build akan gagal.

Langkah:
1. Buka [Firebase Console](https://console.firebase.google.com/) → buat project (atau pilih yang ada).
2. Tambahkan aplikasi Android dengan package name:
   ```
   com.yusuffdllh.smartfinance
   ```
3. Aktifkan **Authentication** → provider **Google** dan **Email/Password**.
4. Untuk Google Sign-In, tambahkan **SHA-1** debug/release keystore Anda di pengaturan project Firebase.
5. Unduh `google-services.json` dan letakkan di:
   ```
   app/google-services.json
   ```

### 3. Konfigurasi AI (di dalam aplikasi)

> [!NOTE]
> Kredensial AI **tidak** disimpan di kode — dikonfigurasi saat runtime lewat aplikasi dan disimpan aman di DataStore perangkat.

Di aplikasi: **Profil → Notifikasi → Pengaturan AI**, isi:
- **Base URL** — endpoint AI (OpenAI-compatible seperti `https://.../v1`, atau Gemini `https://generativelanguage.googleapis.com/v1beta`).
- **API Key** — kunci API Anda.
- **Model** — nama model (mis. `claude-opus-4.8`, `gpt-4o-mini`, `gemini-1.5-flash`, dst).

Layanan AI otomatis mendeteksi dialek:
- Key berawalan `sk-` atau Base URL diakhiri `/v1` → **format OpenAI** (`/chat/completions`, header `Authorization: Bearer`).
- Selain itu → **format Gemini** (`:generateContent`, `?key=`).

### 4. Build & Run

```bash
./gradlew assembleDebug
```

Atau jalankan langsung dari Android Studio.

### 5. Test

```bash
./gradlew testDebugUnitTest
```

---

## 🔐 Deteksi Transaksi Otomatis

### Notification Reader
Menggunakan `NotificationListenerService` untuk membaca **hanya** notifikasi yang berkaitan dengan transaksi (mis. BRImo, Livin, BCA Mobile, BNI, SeaBank, DANA, OVO, GoPay, ShopeePay, dll). Hasil parsing dibuat sebagai **draft** — user wajib konfirmasi sebelum disimpan.

> Izin diaktifkan lewat: **Profil → Notifikasi → Pembaca Notifikasi Bank**.

### Gmail Sync
Menggunakan **Gmail Readonly Scope** untuk mencari bukti transaksi (struk/invoice) di inbox, lalu diproses AI. Sinkronisasi berjalan periodik via WorkManager, dan bisa dipicu manual lewat tombol **"Sinkronkan Sekarang"** di Pengaturan Notifikasi.

> [!NOTE]
> Aplikasi hanya meminta scope **readonly** — tidak pernah mengirim email.

---

## 🔒 Keamanan

Aplikasi **tidak pernah** menyimpan Password, PIN, OTP, CVV, atau Nomor Kartu. Data sensitif dienkripsi, dan kredensial AI hanya berada di DataStore perangkat.

---

## 📄 Lisensi

Belum ditentukan. Tambahkan file `LICENSE` sesuai kebutuhan.
