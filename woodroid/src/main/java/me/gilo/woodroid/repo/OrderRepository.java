package me.gilo.woodroid.repo;

import me.gilo.woodroid.data.api.OrderAPI;
import me.gilo.woodroid.models.Order;
import me.gilo.woodroid.models.OrderNote;
import me.gilo.woodroid.repo.order.OrderNoteRepository;
import me.gilo.woodroid.repo.order.RefundRepository;
import retrofit2.Call;

import java.util.List;

public class OrderRepository extends WooRepository {

    private final OrderAPI apiService;

    OrderNoteRepository orderNoteRepository;
    RefundRepository refundRepository;

    public OrderRepository(String baseUrl, String consumerKey, String consumerSecret) {
       super("orders", baseUrl, consumerKey, consumerSecret);
       apiService = retrofit.create(OrderAPI.class);

       orderNoteRepository = new OrderNoteRepository(baseUrl, consumerKey, consumerSecret);
       refundRepository = new RefundRepository(baseUrl, consumerKey, consumerSecret);
    }

    public Call<Order> create(Order order) {
        return apiService.create(order);
    }

    public Call<Order> order(int id) {
        return apiService.view(id);
    }

    public Call<List<Order>> orders() {
        return apiService.list();
    }

    public Call<Order> update(int id, Order order) {
        return apiService.update(id, order);
    }

    public Call<Order> delete(int id) {
        return apiService.delete(id);
    }

    public Call<Order> delete(int id, boolean force) {
        return apiService.delete(id, force);
    }


    public Call<OrderNote> createNote(Order order, OrderNote note) {
        return orderNoteRepository.create(order, note);
    }

    public Call<OrderNote> note(Order order, int id) {
        return orderNoteRepository.note(order, id);
    }

    public Call<List<OrderNote>> notes(Order order) {
        return orderNoteRepository.notes(order);
    }

    public Call<OrderNote> deleteNote(Order order, int id) {
        return orderNoteRepository.delete(order, id);
    }

    public Call<OrderNote> deleteNote(Order order, int id, boolean force) {
        return orderNoteRepository.delete(order, id, force);
    }


}