package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repositorio;
    Optional<Serie> serieEncontrada;

    public List<SerieDTO> obterTodasAsSeries() {
        return convertDadosSerie(repositorio.findAll());
    }

    public List<SerieDTO> obterTop5Series(){
        return  convertDadosSerie(repositorio.findTop5ByOrderByAvaliacaoDesc());

    }

    public List<SerieDTO> obterLancamentosRecentes(){
        return convertDadosSerie(repositorio.encontrarEpisodiosMaisRecentes());
    }



    public SerieDTO obterPorId(Long id) {

        serieEncontrada = repositorio.findById(id);

        if(serieEncontrada.isPresent()){
            Serie s = serieEncontrada.get();
            return  new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {

        serieEncontrada = repositorio.findById(id);

        if(serieEncontrada.isPresent()){
            Serie s = serieEncontrada.get();
            return convertDadosEpisodios(s);
        }
        return null;
    }

    public List<EpisodioDTO> obterEpisodiosTemporada(Long id, Long numero) {

            return repositorio.obterEpisodiosPorTemporada(id, numero)
                    .stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .collect(Collectors.toList());

    }

    public List<SerieDTO> obterTemporadaPorGenero(String nomeGenero) {

        Categoria categoria = Categoria.fromStringPortugues(nomeGenero);
        return convertDadosSerie(repositorio.findByGenero(categoria));
    }

    public List<EpisodioDTO> obterTop5Episodios(Long id) {

        return repositorio.obterTop5Episodios(id)
                .stream()
                .map(e-> new EpisodioDTO(e.getTemporada(),e.getNumeroEpisodio(),e.getTitulo()))
                .collect(Collectors.toList());

    }

    private List<SerieDTO> convertDadosSerie(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    private List<EpisodioDTO> convertDadosEpisodios(Serie s){

        return s.getEpisodios().stream()
                .map(e -> new EpisodioDTO(e.getTemporada(),e.getNumeroEpisodio(),e.getTitulo()))
                .collect(Collectors.toList());
    }
}