package asciimon;

public class PlayerController {
	public void Move (Player _player, char[][] _tab, int[][] _tabAspect, char _direction)
	{
		if (_direction == 'z' && PossibleMove(_player, _tab, _direction)) {
			MoveTo(_player, _tab, _tabAspect, -1, 0);
		}else if (_direction == 's' && PossibleMove(_player, _tab, _direction)) {
			MoveTo(_player, _tab, _tabAspect, 1, 0);
		}else if (_direction == 'q' && PossibleMove(_player, _tab, _direction)) {
			MoveTo(_player, _tab, _tabAspect, 0, -1);
		}else if (_direction == 'd' && PossibleMove(_player, _tab, _direction)) {
			MoveTo(_player, _tab, _tabAspect, 0, 1);
		}
	}

	private void MoveTo (Player _player, char[][] _tab, int[][] _tabAspect, int _x, int _y)
	{
		if (_tabAspect[_player.getX()][_player.getY()] == 1) {
			_tab[_player.getX()][_player.getY()] = '/';
		}else if (_tabAspect[_player.getX()][_player.getY()] == 2){
			_tab[_player.getX()][_player.getY()] = '+';
		}else if (_tabAspect[_player.getX()][_player.getY()] == 3) {
			_tab[_player.getX()][_player.getY()] = '$';
		}else if (_tabAspect[_player.getX()][_player.getY()] == 4) {
			_tab[_player.getX()][_player.getY()] = '*';
		}else {
			_tab[_player.getX()][_player.getY()] = ' ';
		}
		_player.setX(_player.getX() + _x);
		_player.setY(_player.getY() + _y);

		_tab[_player.getX()][_player.getY()] = '@';
	}

	// Function returns if the movement is possible
	private boolean PossibleMove (Player _player, char[][] _tab, char _direction)
	{
		if (_direction == 'z') {
			return _tab[_player.getX() - 1][_player.getY()] == ' ' || _tab[_player.getX() - 1][_player.getY()] == '/' || _tab[_player.getX() - 1][_player.getY()] == '+' || _tab[_player.getX() - 1][_player.getY()] == '$'||_tab[_player.getX() - 1][_player.getY()] == '*';
		}else if (_direction == 's') {
			return _tab[_player.getX() + 1][_player.getY()] == ' ' || _tab[_player.getX() + 1][_player.getY()] == '/' || _tab[_player.getX() + 1][_player.getY()] == '+' || _tab[_player.getX() + 1][_player.getY()] == '$'||_tab[_player.getX() + 1][_player.getY()] == '*';
		}else if (_direction == 'q') {
			return _tab[_player.getX()][_player.getY() - 1] == ' ' || _tab[_player.getX()][_player.getY() - 1] == '/' || _tab[_player.getX()][_player.getY() - 1] == '+' || _tab[_player.getX()][_player.getY() - 1] == '$'||_tab[_player.getX()][_player.getY() - 1] == '*';
		}else if (_direction == 'd') {
			return _tab[_player.getX()][_player.getY() + 1] == ' ' || _tab[_player.getX()][_player.getY() + 1] == '/' || _tab[_player.getX()][_player.getY() + 1] == '+' || _tab[_player.getX()][_player.getY() + 1] == '$'||_tab[_player.getX()][_player.getY() + 1] == '*';
		}
		return false;
	}
 
	public boolean MovementAvailable (char _res)
	{
		if (_res != 'z' || _res != 'q' || _res != 's' || _res != 'd') return false;

		return true;
	}
}
