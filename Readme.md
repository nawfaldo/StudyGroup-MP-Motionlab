1. git init: Perintah ini digunakan untuk menginisialisasi repository Git baru di dalam direktori saat ini. Setelah menjalankan perintah ini, Git akan mulai melacak perubahan file di direktori tersebut.

2. git remote add origin <URL>: Perintah ini menambahkan remote repository (biasanya dari layanan seperti GitHub atau GitLab) dengan nama origin. Remote ini digunakan untuk menandai alamat repository online tempat kita akan menyimpan atau menarik (pull) perubahan kode.

3. git add .: Perintah ini menambahkan semua perubahan (file baru, modifikasi, atau penghapusan) ke staging area, yaitu area sementara sebelum file dikonfirmasi (commit) dalam versi repository.

4. git commit -m "init": Perintah ini menyimpan perubahan yang ada di staging area ke dalam repository dengan pesan commit "init". Pesan ini berguna untuk mendeskripsikan perubahan apa yang terjadi dalam commit ini.

5. git push -u origin main: Perintah ini mengirimkan (push) commit yang sudah dibuat ke remote repository di cabang (branch) main. Opsi -u mengatur origin main sebagai cabang utama, sehingga perintah git push berikutnya dapat dijalankan tanpa menentukan remote dan branch lagi.

6. git status: Perintah ini menampilkan status file di direktori kerja, apakah ada yang belum ditambahkan ke staging area, ada yang siap di-commit, atau jika ada perubahan yang belum di-push ke remote.

7. git branch: Perintah ini menampilkan daftar cabang yang ada di repository lokal dan menunjukkan cabang aktif saat ini.

8. git branch example: Perintah ini membuat cabang baru bernama example. Cabang ini adalah salinan dari cabang aktif saat ini dan dapat digunakan untuk mengembangkan fitur atau eksperimen baru tanpa memengaruhi cabang utama.

9. git checkout example: Perintah ini digunakan untuk berpindah ke cabang example, menjadikannya cabang aktif saat ini. Setelah beralih ke cabang ini, semua perubahan dan commit baru akan dilakukan di dalam cabang example.
