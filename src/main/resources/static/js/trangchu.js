document.addEventListener("DOMContentLoaded", function () {
    // Bước 1: Gọi API lấy danh sách loại sản phẩm
    fetch('/api/loaisanpham')  // API trả danh sách LoaiSanPham (id, tenLoai)
        .then(response => response.json())
        .then(data => {
            const nav = document.getElementById("navLoaiSanPham");
            data.forEach(loai => {
                // Tạo dropdown menu
                const li = document.createElement("li");
                li.classList.add("nav-item", "dropdown");

                li.innerHTML = `
      <a class="nav-link dropdown-toggle" href="#" id="dropdown-${loai.id}" role="button" data-bs-toggle="dropdown" aria-expanded="false">
        ${loai.tenLoai}
      </a>
      <ul class="dropdown-menu" aria-labelledby="dropdown-${loai.id}" id="dropdown-items-${loai.id}">
        <li><span class="dropdown-item">Đang tải...</span></li>
      </ul>
    `;

                nav.appendChild(li);

                // Bước 2: Lấy danh sách thương hiệu thuộc loại này
                fetch(`/api/thuonghieu/loai/${loai.id}`)
                    .then(response => response.json())
                    .then(thuongHieus => {
                        const dropdown = document.getElementById(`dropdown-items-${loai.id}`);
                        dropdown.innerHTML = ""; // Xóa "Đang tải..."

                        if (thuongHieus.length === 0) {
                            dropdown.innerHTML = `<li><span class="dropdown-item disabled">Không có</span></li>`;
                        } else {
                            thuongHieus.forEach(th => {
                                const item = document.createElement("li");
                                item.innerHTML = `<a class="dropdown-item" href="/sanpham/thuonghieu/${th.id}">${th.tenThuongHieu}</a>`;
                                dropdown.appendChild(item);
                            });
                        }
                    });
            });
        });
});
