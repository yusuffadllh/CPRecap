# AGENTS.md

# SmartFinance AI Development Rules

## Project Overview

SmartFinance adalah aplikasi Android untuk manajemen keuangan pribadi menggunakan Kotlin + Jetpack Compose.

Target aplikasi:

- Personal Finance
- Offline First
- Modern UI
- AI Assisted
- Google Authentication
- Automatic Transaction Detection
- Budget Management
- Analytics

Aplikasi bukan mobile banking.

Aplikasi tidak melakukan transfer, pembayaran, top up ataupun menyimpan PIN dan OTP.

---

# Tech Stack

Language

- Kotlin

UI

- Jetpack Compose
- Material 3

Architecture

- Clean Architecture
- MVVM
- Repository Pattern

Dependency Injection

- Hilt

Database

- Room Database

Cloud

- Firebase Authentication

AI

- Google Gemini API

Background

- WorkManager

Storage

- DataStore

Navigation

- Navigation Compose

---

# Authentication

Gunakan Google Sign In,Email Password dan register

Semua user menggunakan Firebase Authentication.

Session harus otomatis login kembali apabila token masih valid.

Logout harus menghapus session Firebase.

---

# Architecture Rules

Gunakan Clean Architecture.

Flow aplikasi wajib:

Presentation

↓

ViewModel

↓

UseCase

↓

Repository

↓

Local / Remote Data Source

↓

Room / Firebase / AI

View tidak boleh langsung mengakses Repository.

Repository tidak boleh mengetahui UI.

ViewModel hanya mengelola state.

---

# Folder Rules

components/

Berisi reusable compose component.

presentation/

Berisi Screen dan ViewModel.

data/

Entity, DAO, Repository Implementation.

domain/

UseCase dan Repository Interface.

navigation/

Semua navigation.

service/

Notification Service, AI Service, Gmail Service.

utils/

Formatter, Helper, Extension.

ui.theme/

Color, Typography, Theme.

---

# Compose Rules

Gunakan Material3.

Gunakan State Hoisting.

Composable harus reusable.

Pisahkan UI dan Logic.

Tidak boleh ada business logic di Composable.

Gunakan rememberSaveable bila diperlukan.

Gunakan LazyColumn untuk list.

Gunakan immutable state.

---

# Naming Rules

Screen

DashboardScreen

ViewModel

DashboardViewModel

UiState

DashboardUiState

Repository

DashboardRepository

Entity

TransactionEntity

DAO

TransactionDao

UseCase

GetTransactionUseCase

---

# UI Rules

Ikuti DESIGN.md.

Jangan mengubah layout tanpa alasan.

Gunakan spacing konsisten.

Gunakan radius konsisten.

Gunakan warna dari Theme.

Semua screen harus support Dark Mode.

---

# Dashboard

Dashboard terdiri dari:

Header

Balance Card

Income Expense Card

Quick Action

Budget Summary

Analytics Chart

Recent Transaction

Bottom Navigation

---

# Transaction

Jenis transaksi:

Income

Expense

Field wajib:

Amount

Category

Date

Name

Note (optional)

Transaction harus bisa:

Create

Update

Delete

Search

Filter

Sort

---

# Budget

Budget berdasarkan kategori.

Hitung otomatis progress.

Jika melebihi budget tampilkan warning.

---

# Analytics

Tampilkan:

Income vs Expense

Category Breakdown

Monthly Trend

Weekly Trend

Summary

---

# Notification Reader

Gunakan NotificationListenerService.

Hanya membaca notifikasi yang berkaitan dengan transaksi.

Contoh:

BRImo

Livin

BCA Mobile

myBCA

BNI

SeaBank

Jenius

DANA

OVO

GoPay

ShopeePay

LinkAja

Parser menghasilkan:

Merchant

Amount

Date

Reference

Transaction Type

Category

Jangan langsung menyimpan transaksi.

Buat Draft terlebih dahulu.

User wajib melakukan konfirmasi.

---

# Gmail Integration

Gunakan Gmail Readonly Scope.

Jangan meminta permission Send Email.

Gunakan hanya email transaksi.

AI membantu membaca:

Merchant

Amount

Date

Category

Receipt

Invoice

Draft Transaction

User tetap melakukan konfirmasi.

---

# AI Rules

AI digunakan untuk:

Auto Category

Merchant Detection

Duplicate Detection

Budget Recommendation

Financial Insight

Monthly Summary

AI tidak boleh menghapus data.

AI tidak boleh mengubah transaksi tanpa konfirmasi user.

Jika confidence <90% tampilkan pilihan kategori kepada user.

---

# Database

Gunakan Room.

Entity utama:

User

Transaction

Category

Budget

Gunakan Flow.

Repository menjadi satu-satunya akses database.

---

# Security

Jangan simpan:

Password

PIN

OTP

CVV

Nomor Kartu

Data sensitif harus terenkripsi.

---

# Performance

Gunakan LazyColumn.

Gunakan remember bila diperlukan.

Hindari recomposition berlebihan.

Jangan melakukan query database di Composable.

Semua operasi berat menggunakan Coroutine.

---

# Error Handling

Gunakan UiState.

Loading

Success

Empty

Error

Semua screen wajib memiliki keempat state tersebut.

---

# Navigation

Splash

↓

Onboarding

↓

Login

↓

Dashboard

↓

Transaction

↓

Budget

↓

Analytics

↓

Profile

↓

Settings

---

# Coding Principles

Selalu gunakan SOLID.

Gunakan DRY.

Gunakan reusable component.

Hindari duplicate code.

Selalu pisahkan UI dan Business Logic.

---

# Future Features

PDF Export

Excel Export

Subscription Tracking


---

# Development Priority

1. Authentication

2. Dashboard

3. Transaction

4. Notification Reader

5. Budget

6. Analytics

7. AI

8. Gmail Integration

9. Backup

10. Theme

---

# Final Rules

Seluruh implementasi harus mengikuti:

- AGENTS.md
- DESIGN.md
- FEATURES.md
- DEVELOPMENT.md

Apabila terdapat konflik antara implementasi dan dokumentasi, maka dokumentasi menjadi acuan utama.

Jangan membuat fitur di luar scope tanpa persetujuan developer.
