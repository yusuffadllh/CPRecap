# 🏗️ CPRecap: Project State & Feature Blueprint

Dokumen ini berisi spesifikasi teknis mendalam mengenai seluruh fitur, logika, dan tata letak yang telah diimplementasikan di **CPRecap** (sebelumnya SmartFinance). Gunakan dokumen ini sebagai panduan utama untuk replikasi atau pengembangan lanjut oleh AI manapun.

---

## 1. Arsitektur & Tech Stack
- **Architecture**: Clean Architecture (Data, Domain, Presentation layers).
- **UI**: Jetpack Compose dengan Material 3.
- **Dependency Injection**: Hilt.
- **Local DB**: Room Database (Entity: Transaction, Budget, Category, TransactionDraft).
- **Remote DB**: Firebase Firestore (Data tersinkronisasi per `userId`).
- **Auth**: Firebase Authentication (Google Sign-In & Email/Password).
- **Background Task**: WorkManager (Gmail Sync) & NotificationListenerService (Bank Alerts).
- **Navigation**: Navigation Compose dengan transisi kustom.

---

## 2. Fitur Keamanan & Sesi
- **Splash Screen**:
    - Progress bar simulasi (1 detik).
    - Verifikasi `isUserLoggedIn` dari Firebase sebelum masuk Dashboard.
- **Login/Register**:
    - Validasi Email (Regex) & Password (min. 6 karakter).
    - Integrasi Credential Manager untuk Google Sign-In.
- **Logout**:
    - Urutan: `signOut()` -> `clearAllTables()` (Room) -> `clearUserPrefs()` (DataStore).
    - Menghilangkan "Ghost Profile" dengan null-check di `ProfileScreen`.
- **Ubah Password**:
    - Wajib Re-authentikasi (input password lama) sebelum mengganti ke password baru.

---

## 3. Dashboard (Home)
- **Header**: Sapaan dinamis ("Selamat Datang, [Nama User]"). Nama diambil dari Firestore.
- **Balance Card**: Menampilkan total saldo (Income - Expense) dengan fitur **Eye Toggle** (Hide/Show balance).
- **Recent Transactions**: List 5 transaksi terakhir dengan tombol "Lihat Semua" ke halaman Transaksi.
- **Bottom Navigation**:
    - 5 Menu: Home, Transaksi, (FAB) Tambah, Laporan, Profil.
    - Floating Action Button (FAB) di tengah untuk akses cepat "Tambah Transaksi".

---

## 4. Halaman Transaksi (List)
- **Header**: Statis "Transaksi" tanpa tombol Back (karena merupakan tab utama).
- **Search Bar**: Fungsional, memfilter daftar transaksi secara real-time berdasarkan judul atau kategori.
- **Layout**: `LazyColumn` dengan `contentPadding(bottom = 110.dp)` untuk mencegah item terpotong oleh Bottom Bar yang melayang.
- **Logic**: Menggunakan `flatMapLatest` pada `userId` untuk memastikan data yang tampil selalu milik user yang sedang login.

---

## 5. Tambah/Edit Transaksi
- **Input**: Title, Amount, Date (DatePicker), Category (BottomSheet).
- **Instant Save Logic**:
    - Klik Simpan -> Simpan ke Room lokal -> Tampilkan Success Screen **INSTAN**.
    - Proses upload ke Firestore berjalan di `repositoryScope` (Latar belakang).
    - Mencegah "Hanging Loading" atau "Timeout" saat internet lambat.
- **Success Screen**: Animasi Lottie (`success_animation.json`) yang diputar berulang dalam kotak ukuran tetap.

---

## 6. CPRecap AI (Sistem Hybrid)
- **Rule Engine (Offline)**:
    - Keyword matching untuk merchant populer (KFC, Mixue, Tokopedia, Grab, dll).
    - Mengatur kategori secara otomatis tanpa internet.
- **Gemini AI Integration**:
    - Fallback jika Rule Engine gagal mengenali transaksi.
    - Prompt Engineering untuk memprediksi kategori transaksi dari nama merchant dan nominal.
    - API Key disimpan di `UserPreferences`.
- **Notification Reader**:
    - `NotificationListenerService` membaca notifikasi bank/e-wallet.
    - Regex robust untuk ekstrak nominal (mendukung Rp, IDR, titik/koma).
    - Mengirim notifikasi sistem: "Draf transaksi baru ditambahkan" sebagai feedback.

---

## 7. Gmail Reader
- **Scope**: `https://www.googleapis.com/auth/gmail.readonly`.
- **Logic**: Mencari email dengan keyword "Bukti Transfer", "Receipt", "QRIS", dsb dalam 24 jam terakhir.
- **WorkManager**: Sinkronisasi otomatis setiap 1 jam di latar belakang.

---

## 8. Backup & Restore
- **Export**: Mengubah seluruh data Room menjadi file `CPRecap_Backup.json`.
- **Import**: Membaca file JSON dan melakukan `INSERT OR REPLACE` ke Room berdasarkan `userId`. Mencegah data ganda.

---

## 9. Detail UI/UX Kritis
- **Transitions**:
    - Global `Crossfade` (Fade In/Out) durasi 300ms.
    - Menghilangkan "White Flicker" dengan membungkus NavHost dalam `Surface` berwarna `#0F172A`.
- **Safe Area**: Seluruh header menggunakan `statusBarsPadding()` agar tidak menabrak jam/baterai HP.
- **Back Navigation**:
    - Mengingat **1 langkah** riwayat halaman sebelumnya.
    - Pesan "Tekan sekali lagi untuk keluar" (Double Back to Exit) pada tab utama.
- **Profile Screen**: Non-blocking loading. Menampilkan struktur menu langsung, data user menyusul dengan `LinearProgressIndicator` di atas.

---

## 10. Konfigurasi Sistem (PENTING)
- **Package Name**: `com.yusuffdllh.smartfinance` (Untuk stabilitas sistem internal).
- **Display Name**: `CPRecap`.
- **Database Name**: `smartfinance_db`.
- **Theme Name**: `Theme.SmartFinance`.
- **Firestore Collection**: `users/{uid}/transactions`.
