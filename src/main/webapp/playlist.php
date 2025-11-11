<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    exit(0);
}

$data_file = 'C:/data-miplaylist/playlist_data.json';

function cargarDatos() {
    global $data_file;
    if (file_exists($data_file)) {
        $contenido = file_get_contents($data_file);
        return json_decode($contenido, true);
    }
    return ['videos' => []];
}

function guardarDatos($datos) {
    global $data_file;
    $directorio = dirname($data_file);
    if (!is_dir($directorio)) {
        mkdir($directorio, 0777, true);
    }
    file_put_contents($data_file, json_encode($datos, JSON_PRETTY_PRINT));
    return true;
}

function obtenerVideoPorId($videos, $id) {
    foreach ($videos as $index => $video) {
        if ($video['id'] === $id) {
            return ['video' => $video, 'index' => $index];
        }
    }
    return null;
}

try {
    if ($_SERVER['REQUEST_METHOD'] === 'GET') {
        // Cargar todos los videos
        $datos = cargarDatos();
        echo json_encode($datos['videos']);
        exit;
    }

    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $action = $_GET['action'] ?? '';
        $datos = cargarDatos();
        
        switch($action) {
            case 'agregar':
                $nombre = $_POST['nombre'] ?? '';
                $link = $_POST['link'] ?? '';
                
                if (empty($nombre) || empty($link)) {
                    echo json_encode(['success' => false, 'message' => 'Nombre y link son requeridos']);
                    exit;
                }
                
                $nuevoVideo = [
                    'id' => uniqid(),
                    'nombre' => $nombre,
                    'link' => $link,
                    'likes' => 0,
                    'favorito' => false
                ];
                
                $datos['videos'][] = $nuevoVideo;
                guardarDatos($datos);
                echo json_encode(['success' => true]);
                break;
                
            case 'eliminar':
                $id = $_GET['id'] ?? '';
                if (empty($id)) {
                    echo json_encode(['success' => false, 'message' => 'ID requerido']);
                    exit;
                }
                
                $resultado = obtenerVideoPorId($datos['videos'], $id);
                if ($resultado) {
                    array_splice($datos['videos'], $resultado['index'], 1);
                    guardarDatos($datos);
                    echo json_encode(['success' => true]);
                } else {
                    echo json_encode(['success' => false, 'message' => 'Video no encontrado']);
                }
                break;
                
            case 'like':
                $id = $_GET['id'] ?? '';
                if (empty($id)) {
                    echo json_encode(['success' => false, 'message' => 'ID requerido']);
                    exit;
                }
                
                $resultado = obtenerVideoPorId($datos['videos'], $id);
                if ($resultado) {
                    $datos['videos'][$resultado['index']]['likes']++;
                    guardarDatos($datos);
                    echo json_encode(['success' => true]);
                } else {
                    echo json_encode(['success' => false, 'message' => 'Video no encontrado']);
                }
                break;
                
            case 'favorito':
                $id = $_GET['id'] ?? '';
                if (empty($id)) {
                    echo json_encode(['success' => false, 'message' => 'ID requerido']);
                    exit;
                }
                
                $resultado = obtenerVideoPorId($datos['videos'], $id);
                if ($resultado) {
                    $datos['videos'][$resultado['index']]['favorito'] = !$datos['videos'][$resultado['index']]['favorito'];
                    guardarDatos($datos);
                    echo json_encode(['success' => true]);
                } else {
                    echo json_encode(['success' => false, 'message' => 'Video no encontrado']);
                }
                break;
                
            default:
                echo json_encode(['success' => false, 'message' => 'Acción no válida']);
                break;
        }
        exit;
    }

    echo json_encode(['success' => false, 'message' => 'Método no permitido']);
    
} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(['success' => false, 'message' => 'Error interno: ' . $e->getMessage()]);
}
?>