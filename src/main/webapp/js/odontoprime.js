
window.addEventListener("load", () => {

	var btnMenu = document.querySelector(".btnMenu");
	console.log(btnMenu);


	function fecharSubmenus() {
		document.querySelectorAll(".ui-menu-list ul").forEach(sub => {
			sub.classList.remove("show-submenu");
		});
	}

	function fecharSubMenusByClick() {
		document.addEventListener("click", (event) => {
			let menuItems = document.querySelectorAll(".ui-menu-list > li");
			var menu = document.querySelector(".ui-menu-list");
			let clicouDentro = false;


			// Verifica se o clique ocorreu dentro de algum item do menu
			menuItems.forEach(item => {
				if (item.contains(event.target)) {
					clicouDentro = true;
				}
			});

			// Se NÃO clicou dentro de um item do menu, fecha o menu
			if (!clicouDentro) {
				menu.classList.remove("showMenu");
				console.log("Menu fechado");
			}

			// Verifica se o clique aconteceu dentro de uma LI, mas FORA do A
			menuItems.forEach(li => {
				let link = li.querySelector("a");

				if (li.contains(event.target) && event.target !== link) {
					fecharSubmenus();
				}
			});
		});
	}

	function showSubMenu() {
		document.querySelectorAll(".ui-menu-list > li > a").forEach(link => {
			link.addEventListener("click", function(event) {
				let submenu = this.nextElementSibling; // Captura o UL logo após o A

				if (submenu && submenu.tagName === "UL") {
					event.preventDefault(); // Impede a navegação imediata
					// **Impede que o evento global feche o submenu imediatamente**
					event.stopPropagation();
					fecharSubmenus(); // Fecha outros submenus antes de abrir um novo
					submenu.classList.add("show-submenu"); // Abre o submenu corretamente
				}
			});
		});
	}
	function showMenu() {
		btnMenu.addEventListener("click", (event) => {
			event.stopPropagation();
			var menu = document.querySelector(".ui-menu-list");
			menu.classList.toggle("showMenu");
			setTimeout(() => {
				console.log("Classe ativa? ", menu.classList.contains("showMenu"));
			}, 1000);
		});
	}

	showMenu();
	showSubMenu();
	fecharSubMenusByClick();

});


