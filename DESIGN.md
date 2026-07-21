# DESIGN.md

# SmartFinance Design Specification

Dokumen ini menjadi acuan seluruh implementasi UI.

Semua Screen wajib mengikuti desain Figma.

Apabila implementasi berbeda dengan desain, maka desain menjadi prioritas.

---

# Design Principles

UI harus:

- Modern
- Clean
- Minimalist
- Fast
- Responsive
- Accessible
- Dark Mode First

Design Style:

- Rounded Card
- Soft Shadow
- Large Spacing
- Consistent Typography

---

# Color Palette

Primary

Green

Digunakan untuk:

- Income
- Success
- Active Button
- Progress

Background

Dark

Digunakan sebagai background utama aplikasi.

Surface

Sedikit lebih terang dari background.

Digunakan pada:

- Card
- Dialog
- Bottom Sheet

Danger

Merah

Untuk:

- Expense
- Delete
- Warning

Warning

Orange

Budget hampir habis.

Info

Blue

Analytics dan informasi.

---

# Typography

Gunakan Material Typography.

Hierarchy:

Display

Headline

Title

Body

Label

Jangan membuat ukuran font custom tanpa alasan.

---

# Radius

Small

8dp

Medium

12dp

Large

16dp

Extra Large

20dp

Card utama menggunakan radius 20dp.

---

# Spacing

Gunakan kelipatan 4.

4

8

12

16

20

24

32

40

48

Jangan menggunakan spacing random.

---

# Components

Reusable Component:

AppCard

PrimaryButton

SecondaryButton

AppTextField

PasswordTextField

TransactionItem

BudgetProgress

Chip

BottomNavigationBar

FloatingActionButton

CategoryItem

LoadingBar

EmptyState

Semua Screen wajib menggunakan component reusable.

---

# Splash Screen

Purpose

Menampilkan logo dan melakukan pengecekan session login.

Flow

Splash

↓

Check Firebase Session

↓

Onboarding

atau

Dashboard

---

# Onboarding

Terdiri dari beberapa halaman.

Menjelaskan fitur utama:

- Dashboard
- Budget
- Analytics
- AI
- Notification Reader

Button:

Skip

Next

Get Started

---

# Login Screen

Authentication hanya menggunakan Google Sign In.

Jangan tampilkan Email dan Password.

Layout:

Logo

↓

Title

↓

Description

↓

Google Button

↓

Privacy Policy

Jika login berhasil:

Dashboard

---

# Dashboard

Purpose

Ringkasan kondisi keuangan.

Urutan Layout

Header

↓

Balance Card

↓

Income Expense Card

↓

Quick Action

↓

Budget Summary

↓

Analytics Chart

↓

Recent Transaction

↓

Bottom Navigation

Quick Action

- Add Income
- Add Expense
- Budget
- Analytics

Recent Transaction

Menampilkan maksimal 5 transaksi terbaru.

Button:

See All

---

# Transaction Screen

Menampilkan seluruh transaksi.

Fitur:

Search

Filter

Sort

Transaction dibagi berdasarkan bulan.

Item terdiri dari:

Icon

Title

Category

Date

Amount

FAB:

Tambah transaksi.

---

# Add Transaction

Field:

Transaction Type

Amount

Category

Name

Date

Note

Validation:

Amount wajib.

Category wajib.

Date wajib.

Button:

Save

Setelah berhasil:

Success Screen

↓

Dashboard

---

# Budget Screen

Menampilkan daftar budget.

Setiap budget memiliki:

Category

Current Amount

Maximum Amount

Progress

Status

Button:

Create Budget

---

# Analytics

Menampilkan:

Income vs Expense

Category Breakdown

Monthly Trend

Weekly Trend

Summary

User dapat memilih:

Week

Month

Year

---

# Profile

Menampilkan:

Avatar

Name

Email

Menu:

Account

Security

Notification

Theme

Backup

About

Logout

---

# Settings

Semua halaman settings menggunakan layout yang sama.

Header

↓

Section

↓

Menu Item

↓

Divider

↓

Menu Item

---

# Security

Fitur:

Biometric

PIN

Device List

Logout All Device

---

# Notification Settings

Pengguna dapat mengaktifkan:

Notification Reader

Budget Reminder

Monthly Summary

Daily Reminder

---

# Backup

Backup ke Cloud.

Restore Data.

User dapat melihat:

Last Backup

Backup Size

Backup Status

---

# Theme

Support:

System

Light

Dark

Accent Color

Perubahan theme harus realtime.

---

# About

Menampilkan:

App Version

Developer

License

Github

Privacy Policy

Terms

---

# Success Screen

Ditampilkan setelah:

Save Transaction

Create Budget

Restore

Backup

Animation:

Success Check

Button:

Back to Dashboard

---

# Navigation Flow

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

Add Transaction

↓

Success

↓

Dashboard

Dashboard juga dapat membuka:

Budget

Analytics

Profile

Settings

---

# Loading State

Semua Screen wajib memiliki:

Loading

Success

Empty

Error

---

# Empty State

Gunakan EmptyState Component.

Contoh:

Belum ada transaksi.

Belum ada budget.

Belum ada analytics.

---

# Error State

Gunakan Card dengan:

Icon

Title

Description

Retry Button

---

# Animation

Gunakan animasi ringan.

Fade

Scale

Slide

Hindari animasi berlebihan.

---

# Accessibility

Semua tombol minimal 48dp.

Gunakan contentDescription.

Kontras warna harus jelas.

---

# Responsive

UI harus mendukung:

Phone kecil

Phone besar

Tablet (future)

---

# Final Rules

Seluruh UI wajib mengikuti file ini.

Jangan membuat layout baru apabila Screen sudah memiliki spesifikasi.

Apabila ada perubahan desain Figma, DESIGN.md harus diperbarui terlebih dahulu sebelum implementasi kode.
