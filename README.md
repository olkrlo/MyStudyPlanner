# ✨ My Study Planner

> Aplikasi To-Do List khusus mahasiswa dengan tampilan cute, aesthetic, dan dominan warna pastel 🌸

---

## 👥 Kelompok

| Nama | NIM |
|------|-----|
| Nona Auliya Wijaya | 5025231301 |
| Dzikrina Hidayani Martin | 5025231311 |

**Mata Kuliah**: Pemrograman Perangkat Begerak  

---

## 📱 Tentang Aplikasi

**My Study Planner** adalah aplikasi Android yang dirancang untuk membantu mahasiswa mencatat dan mengelola tugas kuliah serta kegiatan belajar sehari-hari. Aplikasi ini dibangun dengan arsitektur **MVVM** dan menggunakan **Room Database** untuk penyimpanan data lokal.

--

## ⚙️ Teknologi yang Digunakan

| Teknologi | Keterangan |
|-----------|------------|
| Kotlin | Bahasa pemrograman utama |
| MVVM Architecture | Pola arsitektur aplikasi |
| Room Database | Penyimpanan data lokal |
| RecyclerView | Menampilkan daftar tugas |
| ViewBinding | Binding layout ke kode |
| Material Design | Komponen UI modern |
| Coroutines | Operasi asynchronous |
| LiveData | Observasi perubahan data |

---

## 🗂️ Struktur Project

```
MyStudyPlanner/
├── data/
│   ├── Task.kt              # Entity Room Database
│   ├── TaskDao.kt           # Data Access Object
│   └── AppDatabase.kt       # Konfigurasi Database
├── repository/
│   └── TaskRepository.kt    # Abstraksi sumber data
├── viewmodel/
│   └── TaskViewModel.kt     # Logic bisnis & state
└── ui/
    ├── MainActivity.kt      # Halaman utama
    ├── AddEditTaskActivity.kt # Form tambah/edit tugas
    └── TaskAdapter.kt       # RecyclerView Adapter
```

---

## ✅ Fitur Aplikasi

- 📋 **Lihat Daftar Tugas** — Semua tugas ditampilkan dalam RecyclerView dengan card berwarna pastel
- ➕ **Tambah Tugas** — Input judul, mata kuliah, deadline, dan prioritas
- ✏️ **Edit Tugas** — Ubah data tugas yang sudah ada
- 🗑️ **Hapus Tugas** — Hapus tugas dengan konfirmasi dialog
- ✅ **Tandai Selesai** — Centang tugas yang sudah selesai (otomatis strikethrough)
- 📊 **Dashboard Statistik** — Tampilkan total, selesai, dan tugas pending

---

## 🎨 Palet Warna

| Warna | Hex | Digunakan untuk |
|-------|-----|-----------------|
| 🟣 Lavender | `#DCC6FF` | Header, FAB, button |
| 🩷 Pink Pastel | `#FFD6E8` | Card prioritas High |
| 🟢 Mint | `#D7FBE8` | Card prioritas Low |
| 🔵 Baby Blue | `#D6EFFF` | Aksen tambahan |
| ⚪ Background | `#FFF8FC` | Latar belakang utama |

---

## 🗃️ Struktur Database

**Tabel: `tasks`**

| Kolom | Tipe | Keterangan |
|-------|------|------------|
| `id` | Int | Primary Key (auto increment) |
| `title` | String | Judul tugas |
| `subject` | String | Nama mata kuliah |
| `deadline` | String | Tanggal deadline |
| `priority` | String | Low / Medium / High |
| `isCompleted` | Boolean | Status selesai |

---

## 🚀 Cara Menjalankan

1. Clone repository ini
   ```bash
   git clone https://github.com/olkrlo/MyStudyPlanner.git
   ```
2. Buka dengan **Android Studio**
3. Tunggu proses **Gradle Sync** selesai
4. Jalankan di emulator atau HP fisik (min. Android 7.0 / API 24)

---

## 🏗️ Arsitektur MVVM

```
UI (Activity/Adapter)
       ↕
  ViewModel
       ↕
  Repository
       ↕
  Room DAO
       ↕
  Database
```

---

- [MVVM Architecture Guide](https://developer.android.com/topic/architecture)
- [Material Design Components](https://material.io/components)
