package pm.astraeus.model

class MissaoResponse(
    var records: List<Missao>,
    var debug: List<DebugInfo>
)

class Missao(
    var id: Int,
    var nome: String,
    var tipo_missao_id: String,
    var tipo_missao_nome: String,
    var tipo_missao_descricao: String,
    var estado_nome: String,
    var estado_descricao: String,
    var estado_cor: String,
    var resultado: String?,
    var inicio: String?,
    var fim: String?,
    var descricao: String,
    var corpos: List<MissaoHasCorpo>
)

class MissaoHasCorpo(
    var corpo_id: Int,
    var corpo_nome: String,
    var imagem: String
)

class DebugInfo(
    var Pathinfo: List<String>?,
    var Module: String?,
    var ID: Int?,
    var Method: String?
)