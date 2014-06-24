examples
========
taken from: http://burtonini.com/computing/mini-dinstall
execute following commands to setup file:

	sudo cp ./mini-dinstall /etc/init.d
	sudo chmod 755 /etc/init.d/mini-dinstall
	sudo chown root:root /etc/init.d/mini-dinstall
	sudo update-rc.d mini-dinstall defaults
